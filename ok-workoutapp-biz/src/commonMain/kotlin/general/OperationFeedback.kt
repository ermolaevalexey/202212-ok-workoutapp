package ru.otus.otuskotlin.workoutapp.biz.general

import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.cor.ICorChainDsl
import ru.otus.otuskotlin.workoutapp.cor.chain
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext

fun ICorChainDsl<WktFeedbackContext>.operation(title: String, command: WktCommand, block: ICorChainDsl<WktFeedbackContext>.() -> Unit) = chain {
  block()
  this.title = title
  on { this.command == command && state == WktState.RUNNING }
}