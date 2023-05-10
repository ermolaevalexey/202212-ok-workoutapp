package ru.otus.otuskotlin.workoutapp.biz.general

import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.chain
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.operation(title: String, command: WktCommand, block: ICorChainDsl<WktWorkoutContext>.() -> Unit) = chain {
  block()
  this.title = title
  on { this.command == command && state == WktState.RUNNING }
}