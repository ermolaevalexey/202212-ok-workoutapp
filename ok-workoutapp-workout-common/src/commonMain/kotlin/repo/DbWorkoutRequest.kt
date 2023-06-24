package ru.otus.otuskotlin.workoutapp.workout.common.repo

import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout

data class DbWorkoutRequest(
  val workout: WktWorkout
)