package ru.otus.otuskotlin.workoutapp.biz

import WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.stubs.WktFeedbackStub

class WktFeedbackProcessor {
  suspend fun exec(ctx: WktFeedbackContext) {
    ctx.feedbackReadResponse = WktFeedbackStub.prepareFeedbackList("id150") as MutableList<WktFeedback>
  }
}