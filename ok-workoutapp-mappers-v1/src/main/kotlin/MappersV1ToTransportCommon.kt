package ru.otus.otuskotlin.workoutapp.mappers.v1

import ru.otus.otuskotlin.workoutapp.api.v1.models.Error
import ru.otus.otuskotlin.workoutapp.common.models.WktError
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktUserId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId


fun WktWorkoutId.toTransport() = this.let { it.asString() }
fun WktFeedbackId.toTransport() = this.let { it.asString() }

fun WktUserId.toTransport() = this.let { it.asString() }
fun List<WktError>.toTransportErrors(): List<Error>? = this
  .map { it.toTransport() }
  .toList()
  .takeIf { it.isNotEmpty() }

private fun WktError.toTransport() = Error(
  code = code.takeIf { it.isNotBlank() },
  group = group.takeIf { it.isNotBlank() },
  field = field.takeIf { it.isNotBlank() },
  message = message.takeIf { it.isNotBlank() },
)