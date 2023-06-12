package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutSearch

data class DbWorkoutSearchRequest(
  val requestObject: WktWorkoutSearch
)