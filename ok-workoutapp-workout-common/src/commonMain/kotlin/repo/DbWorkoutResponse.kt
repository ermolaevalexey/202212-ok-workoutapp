package ru.otus.otuskotlin.workoutapp.workout.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.repo.IDbResponse
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout

data class DbWorkoutResponse(
  override val data: WktWorkout?,
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<WktWorkout> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbWorkoutResponse(null, true)
    fun success(result: WktWorkout) = DbWorkoutResponse(result, true)
    fun error(errors: List<WktError>) = DbWorkoutResponse(null, false, errors)
    fun error(error: WktError) = DbWorkoutResponse(null, false, listOf(error))

  }
}