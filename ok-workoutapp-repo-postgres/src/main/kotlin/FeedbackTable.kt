package ru.otus.otuskotlin.workoutapp.repo.postgres

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload

object FeedbackTable : Table("feedback") {
  val id = varchar("id", 128).uniqueIndex()
  val review = varchar("review", 500)
  val rating = double("rating").default(0.0)
  val userId = reference("user", UserTable.id)
  val workoutId = reference("workout", WorkoutTable.id)

  override val primaryKey = PrimaryKey(FeedbackTable.id)

  fun from(stmt : InsertStatement<Number>) = WktFeedback(
    id = WktFeedbackId(stmt[id]),
    review = stmt[review],
    rating = stmt[rating],
    workout = WktWorkoutId(stmt[workoutId]),
    user = UserTable.select { id.eq(stmt[userId]) }.map { UserTable.from(it) }.first()
  )

  fun from(res : ResultRow) = WktFeedback(
    id = WktFeedbackId(res[id]),
    review = res[review],
    rating = res[rating],
    workout = WktWorkoutId(res[workoutId]),
    user = UserTable.select { id.eq(res[userId]) }.map { UserTable.from(it) }.first()
  )

  fun to(it: UpdateBuilder<*>, feedback: WktFeedbackPayload, randomUuid: () -> String) {
    it[id] = feedback.id.takeIf { it != WktFeedbackId.NONE }?.asString() ?: randomUuid()
    it[review] = feedback.review
    it[rating] = feedback.rating
    it[userId] = feedback.userId.toString()
    it[workoutId] = feedback.workoutId.toString()
  }
}