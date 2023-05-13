package ru.otus.otuskotlin.workoutapp.biz.workers

import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.initStatus(title: String) = worker() {
  this.title = title
  on { state == WktState.NONE }
  handle { state = WktState.RUNNING }
}