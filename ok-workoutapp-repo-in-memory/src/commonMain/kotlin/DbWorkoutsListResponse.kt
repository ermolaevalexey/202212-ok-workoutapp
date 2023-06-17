package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutSearchPayload

data class DbWorkoutsListResponse(
  override val data: WktWorkoutSearchPayload,
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<WktWorkoutSearchPayload> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbWorkoutsListResponse(WktWorkoutSearchPayload(), true)
    fun success(result: WktWorkoutSearchPayload) = DbWorkoutsListResponse(result, true)
    fun error(errors: List<WktError>) = DbWorkoutsListResponse(WktWorkoutSearchPayload(), false, errors)
    fun error(error: WktError) = DbWorkoutsListResponse(WktWorkoutSearchPayload(), false, listOf(error))
  }
}