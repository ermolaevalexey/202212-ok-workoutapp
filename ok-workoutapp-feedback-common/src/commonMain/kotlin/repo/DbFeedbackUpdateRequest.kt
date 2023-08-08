package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload

data class DbFeedbackUpdateRequest(
  val feedbackId: WktFeedbackId,
  val workoutId: WktWorkoutId,
  val data: WktFeedbackPayload
)