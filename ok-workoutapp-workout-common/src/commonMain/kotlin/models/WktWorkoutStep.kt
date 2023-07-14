package ru.otus.otuskotlin.workoutapp.workout.common.models

data class WktWorkoutStep(
  var title: String = "",
  val order: Int = 1,
  var description: String = "",
  var timeCode: String = ""  ,
)
