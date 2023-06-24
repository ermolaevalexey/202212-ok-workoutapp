package ru.otus.otuskotlin.workoutapp.mappers.v1

import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackUser
import ru.otus.otuskotlin.workoutapp.mappers.v1.exceptions.UnknownWktCommand

fun WktFeedbackContext.toTransport(): Response = when (val cmd = command) {
  WktCommand.FEEDBACK_CREATE -> toTransportFeedbackCreate()
  WktCommand.FEEDBACK_READ -> toTransportFeedbackRead()
  WktCommand.FEEDBACK_UPDATE -> toTransportFeedbackUpdate()
  WktCommand.FEEDBACK_DELETE -> toTransportFeedbackDelete()
  else -> throw UnknownWktCommand(cmd)
}
fun WktFeedbackContext.toTransportFeedbackCreate() = FeedbackCreateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackCreateResponse.toTransportFeedback()
)
fun WktFeedbackContext.toTransportFeedbackRead() = FeedbackReadResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackReadResponse.map { it.toTransportFeedback() }
)
fun WktFeedbackContext.toTransportFeedbackUpdate() = FeedbackUpdateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackUpdateResponse.toTransportFeedback()
)
fun WktFeedbackContext.toTransportFeedbackDelete() = FeedbackDeleteResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackDeleteResponse.toTransport()
)

private fun WktFeedback.toTransportFeedback() = Feedback(
  id = this.id.toTransport(),
  workout = this.workout.toTransport(),
  review = this.review,
  rating = this.rating,
  user = this.user?.toTransport()
)

private fun WktFeedbackUser.toTransport() = FeedbackUser(
  id = this.id.toTransport(),
  name = this.name
)

private fun WktFeedbackPayload.toTransport() = FeedbackDeleteResponsePayload(
  id = this.id.toTransport(),
  workout = this.workout.toTransport()
)
