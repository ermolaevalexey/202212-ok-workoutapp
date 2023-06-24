package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.repo.IDbResponse
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback

data class DbFeedbackResponse(
  override val data: WktFeedback? = null,
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<WktFeedback> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbFeedbackResponse(null, true)
    fun success(result: WktFeedback) = DbFeedbackResponse(result, true)
    fun error(errors: List<WktError>) = DbFeedbackResponse(null,false, errors)
    fun error(error: WktError) = DbFeedbackResponse(null,false, listOf(error))
  }
}