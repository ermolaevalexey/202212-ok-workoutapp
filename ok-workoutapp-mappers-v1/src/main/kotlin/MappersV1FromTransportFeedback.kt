import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkMode
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackUser
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
  feedbackCreateRequest = request.feedback.toInternal()
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
  feedbackUpdateRequest = request.feedback.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktFeedbackContext.fromTransport(request: FeedbackDeleteRequest) {
  command = WktCommand.FEEDBACK_DELETE
  requestId = request.requestId()
  feedbackDeleteRequest = request.feedback.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}
private fun FeedbackCreateRequestPayload?.toInternal() = WktFeedbackPayload(
  workout = this?.workout.toWktWorkoutId(),
  review = this?.review ?: "",
  rating = this?.rating ?: 0.0,
  user = this?.user.toWktUserId()
)

private fun FeedbackUpdateRequestPayload?.toInternal() = WktFeedbackPayload(
  id = this?.id.toWktFeedbackId(),
  review = this?.review ?: "",
  rating = this?.rating ?: 0.0,
  user = this?.user.toWktUserId()
)

private fun FeedbackDeleteRequestPayload?.toInternal() = WktFeedbackPayload(
  id = this?.id.toWktFeedbackId(),
  user = this?.user.toWktUserId()
)

private fun FeedbackUser?.toInternal() = WktFeedbackUser(
  id = this?.id.toWktUserId(),
  name = this?.name ?: ""
)