package ru.otus.otuskotlin.workoutapp.workout.common.models

data class WktWorkoutSearch(
  var groupBy: List<WktWorkoutSearchGroupBy> = mutableListOf()
)