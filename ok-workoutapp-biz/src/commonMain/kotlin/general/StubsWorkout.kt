package ru.otus.otuskotlin.workoutapp.biz.general

import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.chain
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.stubs(title: String, block: ICorChainDsl<WktWorkoutContext>.() -> Unit) = chain {
  block()
  this.title = title
  on { workMode == WktWorkMode.STUB && state == WktState.RUNNING }
}