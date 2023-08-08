package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class DbFeedbackIdWorkoutIdRequest(
  val workoutId: WktWorkoutId,
  val feedbackId: WktFeedbackId
)
