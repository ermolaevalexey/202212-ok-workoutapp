package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.stubValidationWorkoutBadTitle(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.BAD_TITLE && state == WktState.RUNNING }
  handle {
    state = WktState.FAILING
    this.errors.add(
      WktError(
        group = "validation",
        code = "validation-title",
        field = "title",
        message = "wrong title field"
      )
    )
  }
}