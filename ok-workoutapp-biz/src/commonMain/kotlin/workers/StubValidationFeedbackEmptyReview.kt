package ru.otus.otuskotlin.workoutapp.biz.workers
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext

fun ICorChainDsl<WktFeedbackContext>.stubValidationFeedbackEmptyReview(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.EMPTY_REVIEW && state == WktState.RUNNING }
  handle {
    state = WktState.FAILING
    this.errors.add(
      WktError(
        group = "validation",
        code = "validation-review",
        field = "review",
        message = "review cannot be empty"
      )
    )
  }
}