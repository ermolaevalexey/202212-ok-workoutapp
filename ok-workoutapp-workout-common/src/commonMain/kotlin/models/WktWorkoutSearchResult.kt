package ru.otus.otuskotlin.workoutapp.workout.common.models

data class WktWorkoutSearchResult(
  var groupName: String = "",
  var workouts: MutableMap<String, List<WktWorkout>> = mutableMapOf()
)