package ru.otus.otuskotlin.workoutapp.feedback.common.helpers

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext

fun WktFeedbackContext.fail(error: WktError) {
  addError(error)
  state = WktState.FAILING
}

fun WktFeedbackContext.addError(vararg error: WktError) = errors.addAll(error)