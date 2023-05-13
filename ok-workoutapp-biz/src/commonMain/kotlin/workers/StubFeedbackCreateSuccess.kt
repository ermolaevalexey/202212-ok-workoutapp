package ru.otus.otuskotlin.workoutapp.biz.workers


import WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.stubs.WktFeedbackStub

fun ICorChainDsl<WktFeedbackContext>.stubCreateFeedbackSuccess(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.SUCCESS && state == WktState.RUNNING }
  handle {
    state = WktState.FINISHING
    val stub = WktFeedbackStub.prepareCreateFeedback(
      "123-444",
      "123",
      "888-000",
      5.0,
      "Good"
    )
    feedbackCreateResponse = stub
  }
}