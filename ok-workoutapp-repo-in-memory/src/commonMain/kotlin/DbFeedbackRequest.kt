package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload

data class DbFeedbackRequest(
  val workoutId: WktWorkoutId,
  val data: WktFeedbackPayload
)