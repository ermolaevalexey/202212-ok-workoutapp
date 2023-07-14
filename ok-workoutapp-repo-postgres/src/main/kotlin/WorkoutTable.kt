package ru.otus.otuskotlin.workoutapp.repo.postgres

import com.benasher44.uuid.Uuid
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgresql.core.Oid.JSON
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktEquipment
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutType

object WorkoutTable : Table("workout") {
  val id = varchar("id", 128)
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
    content = WorkoutContentTable.select{ id eq stmt[id] }.map { WorkoutContentTable.from(it) }.first()
  )

  fun from(row : ResultRow) = WktWorkout(
    id = WktWorkoutId(row[id].toString()),
    title = row[title],
    description = row[description],
    type = row[type],
    equipment = row[equipment],
    rating = row[rating],
    content = WorkoutContentTable.select{ id eq row[id] }.map { WorkoutContentTable.from(it) }.first()
  )

  fun to(it: UpdateBuilder<*>, wkt: WktWorkout, randomUuid: () -> String) {
    it[id] = wkt.id.takeIf { it != WktWorkoutId.NONE }?.asString() ?: randomUuid()
    it[title] = title
    it[description] = description
    it[type] = type
    it[equipment] = equipment
    it[rating] = rating
  }
}