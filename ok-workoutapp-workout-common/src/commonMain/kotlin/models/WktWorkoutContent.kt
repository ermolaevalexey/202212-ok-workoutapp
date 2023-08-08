package ru.otus.otuskotlin.workoutapp.workout.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktWorkoutContent(
  var id: String? = null,
  var workoutId: WktWorkoutId = WktWorkoutId.NONE,
  var video: String = "",
  var steps: List<WktWorkoutStep>? = mutableListOf()
)
