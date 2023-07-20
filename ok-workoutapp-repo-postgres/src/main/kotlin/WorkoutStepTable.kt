package ru.otus.otuskotlin.workoutapp.repo.postgres

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.repo.postgres.WorkoutContentTable.uniqueIndex
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutStep


object WorkoutStepTable : Table("workoutStep") {

  val id = long("id").autoIncrement().uniqueIndex()
  val title = varchar("title", 50)
  val description = varchar("description", 150)
  val timeCode = varchar("timeCode", 5)
  val order = integer("order")

  val workoutId = reference("workoutId", WorkoutContentTable.workoutId)
  override val primaryKey = PrimaryKey(workoutId)

  fun from(res : ResultRow) = WktWorkoutStep(
    description = res[description],
    title = res[title],
    timeCode = res[timeCode],
    order = res[order]
  )

  fun to(it: UpdateBuilder<*>, step: WktWorkoutStep) {
    // it[id] = step.id.inc()
    it[title] = step.title
    it[description] = step.description
    it[timeCode] = step.timeCode
    it[order] = step.order
    it[workoutId] = step.workoutId.asString()
  }
}