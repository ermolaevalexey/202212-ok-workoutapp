package ru.otus.otuskotlin.workoutapp.feedback.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktUserId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class WktFeedbackPayload (
  var workout: WktWorkoutId = WktWorkoutId.NONE,
  var id: WktFeedbackId = WktFeedbackId.NONE,
  var review: String = "",
  var rating: Double = 0.0,
  var user: WktUserId? = WktUserId.NONE
)