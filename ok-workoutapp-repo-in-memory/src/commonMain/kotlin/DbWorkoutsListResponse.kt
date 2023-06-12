package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkout

data class DbWorkoutsListResponse(
  override val data: List<WktWorkout>,
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<List<WktWorkout>> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbWorkoutsListResponse(emptyList(), true)
    fun success(result: List<WktWorkout>) = DbWorkoutsListResponse(result, true)
    fun error(errors: List<WktError>) = DbWorkoutsListResponse(emptyList(), false, errors)
    fun error(error: WktError) = DbWorkoutsListResponse(emptyList(), false, listOf(error))
  }
}