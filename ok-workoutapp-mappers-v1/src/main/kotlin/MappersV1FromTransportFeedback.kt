package ru.otus.otuskotlin.workoutapp.mappers.v1

import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.models.*
import ru.otus.otuskotlin.workoutapp.mappers.v1.exceptions.UnknownRequestClass

fun WktFeedbackContext.fromTransport(request: Request) = when (request) {
  is FeedbackCreateRequest -> fromTransport(request)
  is FeedbackReadRequest -> fromTransport(request)
  is FeedbackDeleteRequest -> fromTransport(request)
  else -> throw UnknownRequestClass(request::class.java)
}

fun WktFeedbackContext.fromTransport(request: FeedbackCreateRequest) {
  command = WktCommand.FEEDBACK_CREATE
  requestId = request.requestId()
  feedbackCreateRequest = request.data.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktFeedbackContext.fromTransport(request: FeedbackReadRequest) {
  command = WktCommand.FEEDBACK_READ
  requestId = request.requestId()
  feedbackReadRequest = request.feedback?.workout.toWktWorkoutId()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktFeedbackContext.fromTransport(request: FeedbackUpdateRequest) {
  command = WktCommand.FEEDBACK_UPDATE
  requestId = request.requestId()
  feedbackUpdateRequest = request.data.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktFeedbackContext.fromTransport(request: FeedbackDeleteRequest) {
  command = WktCommand.FEEDBACK_DELETE
  requestId = request.requestId()
  feedbackDeleteRequest = request.data.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}
private fun FeedbackCreateRequestAllOfData?.toInternal() = WktFeedbackCreateRequest(
  workoutId = this?.workoutId.toWktWorkoutId(),
  data = WktFeedbackPayload(
    review = this?.payload?.review ?: "",
    rating = this?.payload?.rating ?: 0.0,
    userId = this?.userId.toWktUserId()
  )
)

private fun FeedbackUpdateRequestAllOfData?.toInternal() = WktFeedbackUpdateRequest(
  feedbackId = this?.feedbackId.toWktFeedbackId(),
  data = WktFeedbackPayload(
    review = this?.payload?.data?.review ?: "",
    rating = this?.payload?.data?.rating ?: 0.0,
    userId = this?.userId.toWktUserId()
  )
)

private fun FeedbackDeleteRequestAllOfData?.toInternal() = WktFeedbackDeleteRequest(
  feedbackId = this?.feedbackId.toWktFeedbackId(),
  userId = this?.userId.toWktUserId()
)

private fun FeedbackUser?.toInternal() = WktFeedbackUser(
  id = this?.id.toWktUserId(),
  name = this?.name ?: ""
)