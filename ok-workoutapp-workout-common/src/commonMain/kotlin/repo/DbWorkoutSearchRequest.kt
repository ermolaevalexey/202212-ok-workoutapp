package ru.otus.otuskotlin.workoutapp.workout.common.repo

import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutSearch

data class DbWorkoutSearchRequest(
  val requestObject: WktWorkoutSearch
)