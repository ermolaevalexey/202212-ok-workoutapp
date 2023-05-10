package ru.otus.otuskotlin.workoutapp.workout.common.helpers

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun WktWorkoutContext.fail(error: WktError) {
  addError(error)
  state = WktState.FAILING
}

fun WktWorkoutContext.addError(vararg error: WktError) = errors.addAll(error)