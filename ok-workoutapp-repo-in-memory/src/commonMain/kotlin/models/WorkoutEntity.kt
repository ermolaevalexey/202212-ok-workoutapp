package ru.otus.otuskotlin.workoutapp.repoInMemory.models

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktEquipment
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutContent
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutType

data class WorkoutEntity(
  val id: String? = null,
  val title: String? = null,
  val description: String? = null,
  val type: WktWorkoutType = WktWorkoutType.NONE,
  val equipment: WktEquipment = WktEquipment.NONE,
  val content: WktWorkoutContent? = WktWorkoutContent(),
  val rating: Double? = 0.0
) {
  constructor(model: WktWorkout): this(
    id = model.id.asString().takeIf { it.isNotBlank() },
    title = model.title.takeIf { it.isNotBlank() },
    description = model.description.takeIf { it.isNotBlank() },
    type = model.type,
    equipment = model.equipment,
    content = model.content,
    rating = model.rating
  )

  fun toInternal() = WktWorkout(
    id = id?.let { WktWorkoutId(it) }?: WktWorkoutId.NONE,
    title = title?: "",
    description = description?: "",
    type = type?.let { WktWorkoutType.valueOf(it.toString()) }?: WktWorkoutType.NONE,
    equipment = equipment?.let { WktEquipment.valueOf(it.toString()) }?: WktEquipment.NONE,
    content = content ?: WktWorkoutContent(),
    rating = rating ?: 0.0
  )
}
