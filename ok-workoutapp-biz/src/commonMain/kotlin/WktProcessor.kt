package ru.otus.otuskotlin.workoutapp.biz

import WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktContext
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.stubs.WktFeedbackStub
import ru.otus.otuskotlin.workoutapp.stubs.WktWorkoutStub
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

class WktProcessor {
  suspend fun exec(ctx: WktContext) {
    if (ctx is WktWorkoutContext) {
      ctx.workoutCreateResponse = WktWorkoutStub.get().copy(
        title = ctx.workoutCreateRequest.title
      )
    } else if (ctx is WktFeedbackContext) {
      ctx.feedbackReadResponse = WktFeedbackStub.prepareFeedbackList("id150") as MutableList<WktFeedback>
    }
  }
}