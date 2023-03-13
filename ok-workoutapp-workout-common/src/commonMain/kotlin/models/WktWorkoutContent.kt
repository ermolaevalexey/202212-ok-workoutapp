package ru.otus.otuskotlin.workoutapp.workout.common.models

data class WktWorkoutContent(
  var video: String = "",
  var steps: List<WktWorkoutStep>? = mutableListOf()
)
