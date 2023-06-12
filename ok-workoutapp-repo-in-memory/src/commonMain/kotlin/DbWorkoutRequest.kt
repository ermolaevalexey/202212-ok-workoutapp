package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout

data class DbWorkoutRequest(
  val workout: WktWorkout
)