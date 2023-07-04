package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.stubs.WktFeedbackStub

fun ICorChainDsl<WktFeedbackContext>.stubReadFeedbackSuccess(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.SUCCESS && state == WktState.RUNNING }
  handle {
    state = WktState.FINISHING
    val stub = WktFeedbackStub.prepareFeedbackList("123")
    feedbackReadResponse = stub.toMutableList()
  }
}