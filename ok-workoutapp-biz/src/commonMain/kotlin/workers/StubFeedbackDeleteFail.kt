package ru.otus.otuskotlin.workoutapp.biz.workers
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext

fun ICorChainDsl<WktFeedbackContext>.stubFeedbackDeleteFail(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.CANNOT_DELETE && state == WktState.RUNNING }
  handle {
    state = WktState.FAILING
    this.errors.add(
      WktError(
        group = "operations",
        code = "operation-feedback-delete",
        field = "description",
        message = "cannot delete feedback by id ${feedbackDeleteRequest.feedbackId}"
      )
    )
  }
}