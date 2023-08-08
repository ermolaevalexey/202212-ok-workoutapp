package ru.otus.otuskotlin.workoutapp.feedback.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktUserId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktFeedbackDeleteRequest(
  var feedbackId: WktFeedbackId = WktFeedbackId.NONE,
  var workoutId: WktWorkoutId = WktWorkoutId.NONE,
  var userId: WktUserId = WktUserId.NONE
)
