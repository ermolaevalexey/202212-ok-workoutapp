package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.stubReadWorkoutReadNotFound(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.NOT_FOUND && state == WktState.RUNNING }
  handle {
    state = WktState.FAILING
    this.errors.add(
      WktError(
        group = "operations",
        code = "operation-workout-read",
        message = "workout by id ${workoutReadRequest.asString()} cannot be found"
      )
    )
  }
}