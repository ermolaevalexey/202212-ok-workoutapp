package ru.otus.otuskotlin.workoutapp.repo.postgres

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.repo.postgres.WorkoutStepTable.uniqueIndex
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutContent

object WorkoutContentTable : Table("workout_content") {
  val id = varchar("id", 128).uniqueIndex()
  val workoutId = reference("workoutId", WorkoutTable.id)
  val video = varchar("video", 150)

  override val primaryKey = PrimaryKey(id)

  fun from(res : ResultRow) = WktWorkoutContent(
    id = res[id],
    video = res[video],
    steps = (WorkoutContentTable leftJoin WorkoutStepTable).select { workoutId eq res[workoutId] }.map { WorkoutStepTable.from(it) }
  )

  fun to(it: UpdateBuilder<*>, wkt: WktWorkoutContent, randomUuid: () -> String) {
    it[id] = wkt.id.takeIf { it != "" } ?: randomUuid()
    it[workoutId] = wkt.workoutId.asString()
    it[video] = wkt.video.takeIf { it.isNotEmpty() } ?: video.toString()
  }
}