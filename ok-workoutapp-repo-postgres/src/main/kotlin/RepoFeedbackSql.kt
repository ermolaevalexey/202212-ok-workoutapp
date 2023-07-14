package ru.otus.otuskotlin.workoutapp.repo.postgres

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.*
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout
import ru.otus.otuskotlin.workoutapp.workout.common.repo.DbWorkoutResponse

class RepoFeedbackSql (
  properties: SqlProperties,
  initObjects: Collection<WktFeedbackPayload> = emptyList(),
  val randomUuid: () -> String = { uuid4().toString() },
) : IFeedbackRepository {

  init {
    val driver = when {
      properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
      else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    Database.connect(
      properties.url, driver, properties.user, properties.password
    )

    transaction {
      if (properties.dropDatabase) SchemaUtils.drop(FeedbackTable)
      SchemaUtils.create(WorkoutTable)
      initObjects.forEach { createFbk(it) }
    }
  }

  private fun createFbk(fbk: WktFeedbackPayload): WktFeedback {
    val res = FeedbackTable.insert {
      to(it, fbk, randomUuid)
    }

    return FeedbackTable.from(res)
  }

  private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
    try {
      transaction {
        block()
      }
    } catch (e: Exception) {
      handle(e)
    }


  private fun transactionWrapper(block: () -> DbFeedbackResponse): DbFeedbackResponse =
    transactionWrapper(block) { DbFeedbackResponse.error(WktError(it.toString())) }

  private fun transactionListWrapper(block: () -> DbFeedbackListResponse): DbFeedbackListResponse =
    transactionWrapper(block) { DbFeedbackListResponse.error(WktError(it.toString())) }

  private fun updateFbk(
    id: WktFeedbackId,
    payload: WktFeedbackPayload,
    block: (payload: WktFeedbackPayload) -> DbFeedbackResponse
  ) = transactionWrapper {
    if (id == WktFeedbackId.NONE) return@transactionWrapper DbFeedbackResponse.errorEmptyId

    val current = FeedbackTable.select { FeedbackTable.id eq id.asString() }
      .firstOrNull()
      ?.let { FeedbackTable.from(it) }

    when {
      current == null -> DbFeedbackResponse.errorNotFound
      else -> block(payload)
    }
  }

  private fun deleteFbk(
    fbkId: WktFeedbackId,
    wktId: WktWorkoutId,
    block: (fbkId: WktFeedbackId) -> DbFeedbackResponse
  ) = transactionWrapper {
    if (fbkId == WktFeedbackId.NONE) return@transactionWrapper DbFeedbackResponse.errorEmptyId

    if (wktId == WktWorkoutId.NONE) return@transactionWrapper DbFeedbackResponse.errorEmptyId

    val current = FeedbackTable.select { FeedbackTable.id eq fbkId.asString() }
      .firstOrNull()
      ?.let { FeedbackTable.from(it) }

    when {
      current == null -> DbFeedbackResponse.errorNotFound
      else -> block(fbkId)
    }
  }

  override suspend fun createFeedback(req: DbFeedbackRequest): DbFeedbackResponse = transactionWrapper {
    DbFeedbackResponse.success(createFbk(req.data.copy(workout = req.workoutId)))
  }

  override suspend fun readFeedback(req: DbFeedbackWorkoutIdRequest): DbFeedbackListResponse = transactionListWrapper {
    val res = FeedbackTable.select {
      WorkoutTable.id eq req.workoutId.asString()
    }
    return@transactionListWrapper DbFeedbackListResponse.success(res.map { FeedbackTable.from(it) })
  }

  override suspend fun updateFeedback(req: DbFeedbackRequest): DbFeedbackResponse = updateFbk(req.feedbackId, req.data) {
    FeedbackTable.update({ FeedbackTable.id eq it.id.asString() }) {
      to(it, req.data.copy(), randomUuid)
    }
    val res = FeedbackTable.select { FeedbackTable.id eq req.feedbackId.asString() }.first()
    DbFeedbackResponse.success(FeedbackTable.from(res))
  }

  override suspend fun deleteFeedback(req: DbFeedbackIdWorkoutIdRequest): DbFeedbackResponse = deleteFbk(req.feedbackId, req.workoutId) {
    FeedbackTable.deleteWhere { id eq req.feedbackId.toString() }
    DbFeedbackResponse.success(it)
  }
}