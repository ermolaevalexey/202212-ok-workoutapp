package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout

data class DbWorkoutIdRequest(
  val id: WktWorkoutId
) {
  constructor(wkt: WktWorkout): this(wkt.id)
}