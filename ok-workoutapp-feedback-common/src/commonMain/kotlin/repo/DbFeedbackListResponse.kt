package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.repo.IDbResponse
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback

data class DbFeedbackListResponse(
  override val data: List<WktFeedback> = emptyList(),
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<List<WktFeedback>> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbFeedbackListResponse(emptyList(), true)
    fun success(result: List<WktFeedback>) = DbFeedbackListResponse(result, true)
    fun error(errors: List<WktError>) = DbFeedbackListResponse(emptyList(), false, errors)
    fun error(error: WktError) = DbFeedbackListResponse(emptyList(), false, listOf(error))
  }
}