package ru.otus.otuskotlin.workoutapp.biz.validation

import ru.otus.otuskotlin.workoutapp.common.helpers.errorValidation
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext
import ru.otus.otuskotlin.workoutapp.workout.common.helpers.fail

fun ICorChainDsl<WktWorkoutContext>.validateDescriptionHasContent(title: String) = worker {
  this.title = title
  on { workoutCreateRequest.description.isEmpty() }
  handle {
    fail(
      errorValidation(
        field = "description",
        violationCode = "noContent",
        description = "field must contain letters"
      )
    )
  }
}