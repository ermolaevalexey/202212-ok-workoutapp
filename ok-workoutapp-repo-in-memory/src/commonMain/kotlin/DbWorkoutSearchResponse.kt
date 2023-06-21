package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutSearchPayload
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutSearchResult

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