package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.stubs.WktWorkoutStub
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.stubCreateWorkoutSuccess(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.SUCCESS && state == WktState.RUNNING }
  handle {
    state = WktState.FINISHING
    val stub = WktWorkoutStub.prepareCreateWorkoutPayload(
      "123",
      WktWorkoutStub.get()
    )
    workoutCreateResponse = stub
  }
}
