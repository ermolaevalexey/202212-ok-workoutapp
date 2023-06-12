package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class DbFeedbackIdWorkoutIdRequest(
  val workoutId: WktWorkoutId,
  val feedbackId: WktFeedbackId
)
