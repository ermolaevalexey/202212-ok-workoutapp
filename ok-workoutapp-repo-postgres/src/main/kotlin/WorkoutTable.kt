package ru.otus.otuskotlin.workoutapp.repo.postgres

import com.benasher44.uuid.Uuid
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgresql.core.Oid.JSON
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.repo.postgres.WorkoutContentTable.uniqueIndex
import ru.otus.otuskotlin.workoutapp.repo.postgres.WorkoutContentTable.workoutId
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktEquipment
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutContent
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutType

object WorkoutTable : Table("workout") {
  val id = varchar("id", 128).uniqueIndex()
  val title = varchar("title", 128)
  val description = varchar("description", 512)
  val type = enumeration("type", WktWorkoutType::class)
  val equipment = enumeration("equipment", WktEquipment::class)
  val rating = double("rating")

  override val primaryKey = PrimaryKey(id)

  fun from(stmt : InsertStatement<Number>) = WktWorkout(
    id = WktWorkoutId(stmt[id].toString()),
    title = stmt[title],
    description = stmt[description],
    type = stmt[type],
    equipment = stmt[equipment],
    rating = stmt[rating],
    content = (WorkoutTable leftJoin WorkoutContentTable)
      .select { workoutId eq stmt[id] }.takeIf { it.toList().isNotEmpty() }?.map { WorkoutContentTable.from(it) }?.firstOrNull() ?: WktWorkoutContent()
  )

  fun from(row : ResultRow) = WktWorkout(
    id = WktWorkoutId(row[id].toString()),
    title = row[title],
    description = row[description],
    type = row[type],
    equipment = row[equipment],
    rating = row[rating],
    content = (WorkoutTable leftJoin WorkoutContentTable)
      .select { workoutId eq row[id] }
      .takeIf { it.toList().isNotEmpty() }?.map { WorkoutContentTable.from(it) }?.firstOrNull() ?: WktWorkoutContent()
  )

  fun to(it: UpdateBuilder<*>, wkt: WktWorkout, randomUuid: () -> String) {
    it[id] = wkt.id.takeIf { it != WktWorkoutId.NONE }?.asString() ?: randomUuid()
    it[title] = wkt.title
    it[description] = wkt.description
    it[type] = wkt.type
    it[equipment] = wkt.equipment
    it[rating] = wkt.rating
  }
}