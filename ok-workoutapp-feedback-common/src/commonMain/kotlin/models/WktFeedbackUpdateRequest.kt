package ru.otus.otuskotlin.workoutapp.feedback.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktFeedbackUpdateRequest(
  var workoutId: WktWorkoutId = WktWorkoutId.NONE,
  var feedbackId: WktFeedbackId = WktFeedbackId.NONE,
  var data: WktFeedbackPayload = WktFeedbackPayload()
)
