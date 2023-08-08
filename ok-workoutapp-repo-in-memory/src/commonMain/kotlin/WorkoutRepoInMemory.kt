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
    val key = req.id.takeIf { it.toString().isNotEmpty() }?.asString() ?: return resultErrorEmptyId

    val wkt = cache.get(key)
    return when (wkt) {
      null -> resultErrorNotFound
      else -> DbWorkoutResponse.success(wkt.toInternal())
    }
  }

  override suspend fun updateWorkout(req: DbWorkoutRequest): DbWorkoutResponse {
    val key = req.workout.id.takeIf { it.toString().isNotEmpty() }?.asString() ?: return resultErrorEmptyId
    val newWkt = req.workout.copy()

    return mutex.withLock {
      val oldWkt = cache.get(key)
      when {
        oldWkt == null -> resultErrorNotFound
        else -> {
          val wktUpdated = oldWkt.copy(
            title = newWkt.title.takeIf { it.isNotEmpty() } ?: oldWkt.title,
            description = newWkt.description.takeIf { it.isNotEmpty() } ?: oldWkt.description,
            content = newWkt.content.takeIf { it.video.isNotEmpty() && (it.steps?.isNotEmpty()) as Boolean } ?: oldWkt.content,
            rating = newWkt.rating.takeIf { it != oldWkt.rating } ?: oldWkt.rating
          )
          cache.put(key, wktUpdated)
          DbWorkoutResponse.success(wktUpdated.toInternal())
        }
      }
    }
  }

  override suspend fun searchWorkouts(req: DbWorkoutSearchRequest): DbWorkoutSearchResponse {
    val preparedResp = cache.asMap().toList().map { it.second.toInternal() }
    return DbWorkoutSearchResponse.success(
      WktWorkoutSearchPayload(
      groups = req.requestObject.groupBy.map { param ->
        groupSearchWorkouts(param, preparedResp)
      }.toMutableList()
    ))
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
    val resultErrorEmptyId = DbWorkoutResponse.error(listOf(
      WktError(
        field = "id",
        message = "id must not be null or blank"
      )
    ))

    val resultErrorNotFound = DbWorkoutResponse.error(listOf(
      WktError(
        code = "not-found",
        field = "id",
        message = "not-found"
      )
    ))
  }
}