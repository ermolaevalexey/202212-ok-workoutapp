package ru.otus.otuskotlin.workoutapp.workout.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.repo.IDbResponse
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutSearchPayload

data class DbWorkoutSearchResponse(
  override val data: WktWorkoutSearchPayload,
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<WktWorkoutSearchPayload> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbWorkoutSearchResponse(WktWorkoutSearchPayload(), true)
    fun success(result: WktWorkoutSearchPayload) = DbWorkoutSearchResponse(result, true)
    fun error(errors: List<WktError>) = DbWorkoutSearchResponse(WktWorkoutSearchPayload(), false, errors)
    fun error(error: WktError) = DbWorkoutSearchResponse(WktWorkoutSearchPayload(), false, listOf(error))
  }
}