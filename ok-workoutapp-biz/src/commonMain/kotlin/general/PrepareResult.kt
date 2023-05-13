package ru.otus.otuskotlin.workoutapp.biz.general

import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.prepareResult(title: String) = worker {
  this.title = title
  description = "Подготовка данных для ответа клиенту на запрос"
  on { workMode != WktWorkMode.STUB }
  handle {
    when (command) {
      WktCommand.WORKOUT_CREATE -> workoutResponse = workoutCreateResponse
      WktCommand.WORKOUT_READ -> workoutResponse = workoutReadResponse
      WktCommand.WORKOUT_UPDATE -> workoutResponse = workoutUpdateResponse
      WktCommand.WORKOUT_SEARCH -> workoutResponse = workoutSearchResponse
      else -> {}
    }
    state = when (val st = state) {
      WktState.RUNNING -> WktState.FINISHING
      else -> st
    }
  }
}

fun ICorChainDsl<WktFeedbackContext>.prepareResult(title: String) = worker {
  this.title = title
  description = "Подготовка данных для ответа"
  on { workMode != WktWorkMode.STUB }
  handle {
    when (command) {
      WktCommand.FEEDBACK_CREATE -> feedbackResponse = feedbackCreateResponse
      WktCommand.FEEDBACK_READ -> feedbackResponse = feedbackReadResponse
      WktCommand.FEEDBACK_UPDATE -> feedbackResponse = feedbackUpdateResponse
      WktCommand.FEEDBACK_DELETE -> feedbackResponse = feedbackDeleteResponse
      else -> {}
    }
    state = when (val st = state) {
      WktState.RUNNING -> WktState.FINISHING
      else -> st
    }
  }
}