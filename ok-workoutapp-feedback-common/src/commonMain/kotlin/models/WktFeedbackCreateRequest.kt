package ru.otus.otuskotlin.workoutapp.feedback.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktFeedbackCreateRequest(
  var workoutId: WktWorkoutId = WktWorkoutId.NONE,
  var data: WktFeedbackPayload = WktFeedbackPayload()
)
