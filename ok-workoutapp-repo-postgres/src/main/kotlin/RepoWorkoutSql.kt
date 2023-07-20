package ru.otus.otuskotlin.workoutapp.repo.postgres

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.workout.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.repo.*

class RepoWorkoutSql (
  properties: SqlProperties,
  initObjects: Collection<WktWorkout> = emptyList(),
  val randomUuid: () -> String = { uuid4().toString() },
) : IWorkoutRepository {

  init {
    val driver = when {
      properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
      else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    Database.connect(
      properties.url, driver, properties.user, properties.password
    )

    transaction {
      if (properties.dropDatabase) {
        SchemaUtils.drop(WorkoutTable)
        SchemaUtils.drop(WorkoutContentTable)
        SchemaUtils.drop(WorkoutStepTable)
      }

      SchemaUtils.create(WorkoutTable)
      SchemaUtils.create(WorkoutContentTable)
      SchemaUtils.create(WorkoutStepTable)
      initObjects.forEach { createWorkout(it) }
    }
  }

  private fun createWorkout(wkt: WktWorkout): WktWorkout {
    val res = WorkoutTable.insert {
      to(it, wkt, randomUuid)
    }

    val wktId = res[WorkoutTable.id]
    WorkoutContentTable.insert {
      to(it, wkt.content.copy(workoutId = WktWorkoutId(wktId)), randomUuid)
    }

    wkt.content.steps?.forEach { step ->
      WorkoutStepTable.insert {
        to(it, step.copy(workoutId = WktWorkoutId(wktId)))
      }
    }

    return WorkoutTable.from(res)
  }

  private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
    try {
      transaction {
        block()
      }
    } catch (e: Exception) {
      handle(e)
    }

  private fun transactionWrapper(block: () -> DbWorkoutResponse): DbWorkoutResponse =
    transactionWrapper(block) { DbWorkoutResponse.error(WktError(it.toString())) }

  private fun transactionSearchWrapper(block: () -> DbWorkoutSearchResponse): DbWorkoutSearchResponse =
    transactionWrapper(block) { DbWorkoutSearchResponse.error(WktError(it.toString())) }

  override suspend fun createWorkout(req: DbWorkoutRequest): DbWorkoutResponse = transactionWrapper {
    DbWorkoutResponse.success(createWorkout(req.workout))
  }

  private fun read(id: WktWorkoutId): DbWorkoutResponse {
    val res = WorkoutTable.select {
      WorkoutTable.id eq id.asString()
    }.singleOrNull() ?: return DbWorkoutResponse.errorNotFound
    return DbWorkoutResponse.success(WorkoutTable.from(res))
  }

  private fun update(
    id: WktWorkoutId,
    block: (WktWorkout) -> DbWorkoutResponse
  ): DbWorkoutResponse =
    transactionWrapper {
      if (id == WktWorkoutId.NONE) return@transactionWrapper DbWorkoutResponse.errorEmptyId

      val current = WorkoutTable.select { WorkoutTable.id eq id.asString() }
        .firstOrNull()
        ?.let { WorkoutTable.from(it) }

      when {
        current == null -> DbWorkoutResponse.errorNotFound
        else -> block(current)
      }
    }

  override suspend fun readWorkout(req: DbWorkoutIdRequest): DbWorkoutResponse = transactionWrapper {
    read(req.id)
  }

  override suspend fun updateWorkout(req: DbWorkoutRequest): DbWorkoutResponse = update(req.workout.id) {
    WorkoutTable.update({ WorkoutTable.id eq req.workout.id.asString() }) {
      to(it, req.workout.copy(), randomUuid)
    }

    read(req.workout.id)
  }

  private fun groupSearchWorkouts(groupBy: WktWorkoutSearchGroupBy, workoutsList: List<WktWorkout>): WktWorkoutSearchResult {
    return when (groupBy) {
      WktWorkoutSearchGroupBy.WORKOUT_TYPE -> WktWorkoutSearchResult(
        groupName = groupBy.toString().lowercase(),
        workouts = workoutsList.groupBy { it.type.toString().lowercase() }.mapValues { it.value.toMutableList() }.toMutableMap()
      )
      WktWorkoutSearchGroupBy.EQUIPMENT -> WktWorkoutSearchResult(
        groupName = groupBy.toString().lowercase(),
        workouts = workoutsList.groupBy { it.equipment.toString().lowercase() }.mapValues { it.value.toMutableList() }.toMutableMap()
      )
      else -> WktWorkoutSearchResult(
        groupName = WktWorkoutSearchGroupBy.NONE.toString().lowercase(),
        workouts = mutableMapOf(WktWorkoutSearchGroupBy.NONE.toString().lowercase() to workoutsList.toMutableList())
      )
    }
  }

  override suspend fun searchWorkouts(req: DbWorkoutSearchRequest): DbWorkoutSearchResponse  =
    transactionSearchWrapper {
      val wkts = WorkoutTable.selectAll().map { WorkoutTable.from(it) }
      if (wkts.isEmpty() ) {
        return@transactionSearchWrapper DbWorkoutSearchResponse.error(WktError(message = "empty list"))
      }

      return@transactionSearchWrapper DbWorkoutSearchResponse.success(
        WktWorkoutSearchPayload(
          groups = req.requestObject.groupBy.map { param ->
            groupSearchWorkouts(param, wkts)
          }.toMutableList()
        )
      )
    }
}