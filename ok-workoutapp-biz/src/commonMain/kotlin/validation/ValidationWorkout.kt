package ru.otus.otuskotlin.workoutapp.biz.validation

import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.chain
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun ICorChainDsl<WktWorkoutContext>.validation(block: ICorChainDsl<WktWorkoutContext>.() -> Unit) = chain {
  block()
  title = "Валидация тренировки"

  on { state == WktState.RUNNING }
}