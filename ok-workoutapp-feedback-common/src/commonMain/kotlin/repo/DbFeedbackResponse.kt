package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.repo.IDbResponse
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.common.helpers.errorNotFound as wktErrorNotFound
import ru.otus.otuskotlin.workoutapp.common.helpers.errorEmptyId as wktErrorEmptyId

data class DbFeedbackResponse(
  override val data: WktFeedback? = null,
  override val isSuccess: Boolean,
  override val errors: List<WktError> = emptyList()
): IDbResponse<WktFeedback> {
  companion object {
    val MOCK_SUCCESS_EMPTY = DbFeedbackResponse(null, true)
    fun success(result: WktFeedback) = DbFeedbackResponse(result, true)

    fun success(result: WktFeedbackId) = DbFeedbackResponse(WktFeedback(id = result), true)
    fun error(errors: List<WktError>) = DbFeedbackResponse(null,false, errors)
    fun error(error: WktError) = DbFeedbackResponse(null,false, listOf(error))

    val errorEmptyId = error(wktErrorEmptyId)

    val errorNotFound = error(wktErrorNotFound)
  }
}