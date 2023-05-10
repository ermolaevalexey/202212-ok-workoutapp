package ru.otus.otuskotlin.workoutapp.biz.validation

import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.chain
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext

fun ICorChainDsl<WktFeedbackContext>.validation(block: ICorChainDsl<WktFeedbackContext>.() -> Unit) = chain {
  block()
  title = "Валидация отзыва"

  on { state == WktState.RUNNING }
}