package ru.otus.otuskotlin.workoutapp.workout.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktWorkoutStep(
  var id: Long = 0L,
  var title: String = "",
  val order: Int = 1,
  var description: String = "",
  var timeCode: String = ""  ,
  var workoutId: WktWorkoutId = WktWorkoutId.NONE
)
