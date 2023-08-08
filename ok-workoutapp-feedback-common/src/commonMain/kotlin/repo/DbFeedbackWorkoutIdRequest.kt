package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class DbFeedbackWorkoutIdRequest(
  val workoutId: WktWorkoutId
)
