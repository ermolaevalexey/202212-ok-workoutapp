package ru.otus.otuskotlin.workoutapp.biz.validation

import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.common.helpers.errorValidation
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.helpers.fail

fun ICorChainDsl<WktFeedbackContext>.validateWorkoutId(title: String) = worker {
  this.title = title
  on {
    feedbackValidity.workout.asString().isEmpty()
  }
  handle {
    fail(
      errorValidation(
        field = "workout",
        violationCode = "empty",
        description = "must not be empty"
      )
    )
  }
}