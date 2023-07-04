package ru.otus.otuskotlin.workoutapp.repoInMemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.repoInMemory.models.WorkoutEntity
import ru.otus.otuskotlin.workoutapp.workout.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class WorkoutRepoInMemory(
  initObjects: List<WktWorkout> = emptyList(),
  ttl: Duration = 2.minutes,
  val randomUuid: () -> String = { uuid4().toString() }
): IWorkoutRepository {

  private val cache = Cache.Builder<String, WorkoutEntity>()
    .expireAfterWrite(ttl)
    .build()

  private val mutex: Mutex = Mutex()

  init {
    initObjects.forEach {
      save(it)
    }
  }

  private fun save(wkt: WktWorkout) {
    val entity = WorkoutEntity(wkt)
    if (entity.id == null) {
      return
    }
    cache.put(entity.id, entity)
  }
  override suspend fun createWorkout(req: DbWorkoutRequest): DbWorkoutResponse {
    val key = randomUuid()
    val wkt = req.workout.copy(id = WktWorkoutId(key))
    val entity = WorkoutEntity(wkt)

    cache.put(key, entity)

    return DbWorkoutResponse(
      data = wkt,
      isSuccess = true
    )
  }

  override suspend fun readWorkout(req: DbWorkoutIdRequest): DbWorkoutResponse {
    val key = req.id.takeIf { it != WktWorkoutId.NONE }?.asString() ?: return resultErrorEmptyId
    return cache.get(key)?.let {
      DbWorkoutResponse(
        data = it.toInternal(),
        isSuccess = true
      )
    } ?: resultErrorNotFound
  }

  override suspend fun updateWorkout(req: DbWorkoutRequest): DbWorkoutResponse {
    val key = req.workout.id.takeIf { it != WktWorkoutId.NONE }?.asString() ?: return resultErrorEmptyId
    val newWorkout = req.workout.copy()
    val entity = WorkoutEntity(newWorkout)
    return mutex.withLock {
      val oldWkt = cache.get(key)
      when {
        oldWkt == null -> resultErrorNotFound
        else -> {
          cache.put(key, entity)
          DbWorkoutResponse(
            data = newWorkout,
            isSuccess = true
          )
        }
      }
    }
  }

  override suspend fun searchWorkouts(req: DbWorkoutSearchRequest): DbWorkoutSearchResponse {
    val preparedResp = cache.asMap().toList().map { it.second.toInternal() }
    return DbWorkoutSearchResponse(
      data = WktWorkoutSearchPayload(
        groups = req.requestObject.groupBy.map { param ->
          groupSearchWorkouts(param, preparedResp)
        }.toMutableList()
      ),
      isSuccess = true
    )
  }

  private fun groupSearchWorkouts(groupBy: WktWorkoutSearchGroupBy, workoutsList: List<WktWorkout>): WktWorkoutSearchResult {
    return when (groupBy) {
        WktWorkoutSearchGroupBy.WORKOUT_TYPE -> WktWorkoutSearchResult(
          groupName = groupBy.toString(),
          workouts = workoutsList.groupBy { it.type.toString() }.mapValues { it.value.toMutableList() }.toMutableMap()
        )
        WktWorkoutSearchGroupBy.EQUIPMENT -> WktWorkoutSearchResult(
          groupName = groupBy.toString(),
          workouts = workoutsList.groupBy { it.equipment.toString() }.mapValues { it.value.toMutableList() }.toMutableMap()
        )
        else -> WktWorkoutSearchResult(
          groupName = WktWorkoutSearchGroupBy.NONE.toString(),
          workouts = mutableMapOf(WktWorkoutSearchGroupBy.NONE.toString() to workoutsList.toMutableList())
        )
    }
  }

  companion object {
    val resultErrorEmptyId = DbWorkoutResponse(
      data = null,
      isSuccess = false,
      errors = listOf(
        WktError(
          field = "id",
          message = "id must not be null or blank"
        )
      )
    )

    val resultErrorNotFound = DbWorkoutResponse(
      data = null,
      isSuccess = false,
      errors = listOf(
        WktError(
          code = "not-found",
          field = "id",
          message = "not-found"
        )
      )
    )
  }
}