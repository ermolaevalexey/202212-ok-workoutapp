package ru.otus.otuskotlin.workoutapp.common.models

import ru.otus.otuskotlin.workoutapp.api.v1.models.Request

interface WktContext {
  var command: WktCommand
  var state: WktState
  var errors: MutableList<WktError>
}

fun WktContext.fromTransport(request: Request) {}
fun WktContext.toTransport() {}