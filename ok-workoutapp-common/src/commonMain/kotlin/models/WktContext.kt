package ru.otus.otuskotlin.workoutapp.common.models

import ru.otus.otuskotlin.workoutapp.api.v1.models.Request
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub

interface WktContext {
  var command: WktCommand
  var state: WktState
  var errors: MutableList<WktError>
  var stubCase: WktStub
}

fun WktContext.fromTransport(request: Request) {}
fun WktContext.toTransport() {}