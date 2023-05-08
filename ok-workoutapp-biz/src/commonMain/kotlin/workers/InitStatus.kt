package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.common.models.WktContext
import ru.otus.otuskotlin.workoutapp.common.models.WktState

fun ICorChainDsl<WktContext>.initStatus(title: String) = worker() {
  this.title = title
  on { state == WktState.NONE }
  handle { state = WktState.RUNNING }
}