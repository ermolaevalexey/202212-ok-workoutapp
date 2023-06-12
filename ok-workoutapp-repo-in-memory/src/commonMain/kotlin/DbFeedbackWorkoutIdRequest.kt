package ru.otus.otuskotlin.workoutapp.repoInMemory

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId

data class DbFeedbackWorkoutIdRequest(
  val workoutId: WktWorkoutId
)
