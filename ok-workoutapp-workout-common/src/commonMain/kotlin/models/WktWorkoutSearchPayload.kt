package ru.otus.otuskotlin.workoutapp.workout.common.models

data class WktWorkoutSearchPayload (
  var groups: MutableList<WktWorkoutSearchResult> = mutableListOf()
)