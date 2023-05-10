package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.common.models.WktContext
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker

fun ICorChainDsl<WktContext>.stubNoCase(title: String) = worker {
  this.title = title
  on { stubCase == WktStub.BAD_DESCRIPTION && state == WktState.RUNNING }
  handle {
    state = WktState.FAILING
    this.errors.add(
      WktError(
        group = "validation",
        code = "stub",
        field = "validation",
        message = "Wrong stub case is requested: ${stubCase.name}"
      )
    )
  }
}