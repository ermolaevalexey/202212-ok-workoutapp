package ru.otus.otuskotlin.workoutapp.workout.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktWorkout(
  var id: WktWorkoutId = WktWorkoutId.NONE,
  var title: String = "",
  var description: String = "",
  var type: WktWorkoutType = WktWorkoutType.NONE,
  var equipment: WktEquipment = WktEquipment.NONE,
  var content: WktWorkoutContent = WktWorkoutContent(),
  var rating: Double = 0.0
)