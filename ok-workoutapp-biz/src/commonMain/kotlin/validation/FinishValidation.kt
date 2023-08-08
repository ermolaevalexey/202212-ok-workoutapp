package ru.otus.otuskotlin.workoutapp.biz.validation

import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.finishValidationWorkout(title: String) = worker {
  this.title = title
  on { state == WktState.RUNNING }
  handle {
    workoutValid = workoutValidity
  }
}

fun ICorChainDsl<WktFeedbackContext>.finishValidationFeedback(title: String) = worker {
  this.title = title
  on { state == WktState.RUNNING }
  handle {
    feedbackValid = feedbackValidity
  }
}