import exceptions.UnknownRequestClass
import models.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import stubs.WktStub

fun WktContext.fromTransport(request: Request) = when (request) {
  is WorkoutCreateRequest -> fromTransport(request)
  is WorkoutReadRequest -> fromTransport(request)
  is WorkoutUpdateRequest -> fromTransport(request)
  is WorkoutSearchRequest -> fromTransport(request)
  is FeedbackCreateRequest -> fromTransport(request)
  is FeedbackUpdateRequest -> fromTransport(request)
  is FeedbackReadRequest -> fromTransport(request)
  is FeedbackDeleteRequest -> fromTransport(request)
  else -> throw UnknownRequestClass(request::class.java)
}

private fun Request?.requestId() = this?.requestId?.let { WktRequestId(it) } ?: WktRequestId.NONE
private fun String?.toWktWorkoutId() = this?.let { WktWorkoutId(it) } ?: WktWorkoutId.NONE
private fun String?.toWktWorkoutWithId() = WktWorkout(id = this.toWktWorkoutId())
private fun String?.toWktUserId() = this?.let { WktUserId(it) } ?: WktUserId.NONE
private fun String?.toWktFeedbackId() = this?.let { WktFeedbackId(it) } ?: WktFeedbackId.NONE

fun WorkoutType.fromTransport() = when (this) {
  WorkoutType.CORE -> WktWorkoutType.CORE
  WorkoutType.ARMS -> WktWorkoutType.ARMS
  WorkoutType.LEGS -> WktWorkoutType.LEGS
  else -> WktWorkoutType.NONE
}

fun Equipment.fromTransport() = when (this) {
  Equipment.OWN_WEIGHT -> WktEquipment.OWN_WEIGHT
  Equipment.BARS -> WktEquipment.BARS
  Equipment.DUMBBELLS -> WktEquipment.DUMBBELLS
  Equipment.HORIZONTAL_BAR -> WktEquipment.HORIZONTAL_BAR
  Equipment.JUMPING_ROPE -> WktEquipment.JUMPING_ROPE
  else -> WktEquipment.NONE
}
fun WktContext.fromTransport(request: WorkoutCreateRequest) {
  command = WktCommand.WORKOUT_CREATE
  requestId = request.requestId()
  workoutCreateRequest = request.workout.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: WorkoutReadRequest) {
  command = WktCommand.WORKOUT_READ
  requestId = request.requestId()
  workoutReadRequest = request.workout?.id.toWktWorkoutId()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: WorkoutUpdateRequest) {
  command = WktCommand.WORKOUT_UPDATE
  requestId = request.requestId()
  workoutUpdateRequest = request.workout.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: WorkoutSearchRequest) {
  command = WktCommand.WORKOUT_SEARCH
  requestId = request.requestId()
  workoutSearchRequest = request.params.toInternal() ?: WktWorkoutSearch()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: FeedbackCreateRequest) {
  command = WktCommand.FEEDBACK_CREATE
  requestId = request.requestId()
  feedbackCreateRequest = request.feedback.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: FeedbackUpdateRequest) {
  command = WktCommand.FEEDBACK_UPDATE
  requestId = request.requestId()
  feedbackUpdateRequest = request.feedback.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: FeedbackDeleteRequest) {
  command = WktCommand.FEEDBACK_DELETE
  requestId = request.requestId()
  feedbackDeleteRequest = request.feedback.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktContext.fromTransport(request: FeedbackReadRequest) {
  command = WktCommand.FEEDBACK_READ
  requestId = request.requestId()
  feedbackReadRequest = request.feedback?.workout.toWktWorkoutWithId()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

private fun WorkoutBase?.toInternal() = WktWorkout(
  title = this?.title ?: "",
  description = this?.description ?: "",
  type = this?.type?.fromTransport() ?: WktWorkoutType.NONE,
  equipment = this?.equipment?.fromTransport() ?: WktEquipment.NONE,
  content = this?.content?.toInternal() ?: WktWorkoutContent()
)

private fun WorkoutUpdateRequestPayloadWorkout?.toInternal() = WktWorkout(
  id = this?.id.toWktWorkoutId(),
  description = this?.description ?: "",
  type = this?.type?.fromTransport() ?: WktWorkoutType.NONE,
  equipment = this?.equipment?.fromTransport() ?: WktEquipment.NONE,
  content = this?.content?.toInternal() ?: WktWorkoutContent()
)

private fun WorkoutBaseContent?.toInternal() = WktWorkoutContent(
  video = this?.video ?: "",
  steps = this?.steps?.map { it.toInternal() }
)

private fun WorkoutStep?.toInternal() = WktWorkoutStep(
  title = this?.title ?: "",
  description = this?.description ?: "",
  timeCode = this?.timecode ?: ""
)

private fun WorkoutSearchRequestPayload?.toInternal() = WktWorkoutSearch(
  groupBy = this?.groupBy?.map { it.fromTransport() } as MutableList<WktWorkoutSearchGroupBy>
)
private fun WorkoutSearchGroupBy?.fromTransport() = when (this) {
  WorkoutSearchGroupBy.EQUIPMENT -> WktWorkoutSearchGroupBy.EQUIPMENT
  WorkoutSearchGroupBy.WORKOUT_TYPE -> WktWorkoutSearchGroupBy.WORKOUT_TYPE
  null -> WorkoutSearchGroupBy.WORKOUT_TYPE
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

private fun Debug?.fromTransportToWorkMode() = when (this?.mode) {
  RequestDebugMode.PROD -> WktWorkMode.PROD
  RequestDebugMode.TEST -> WktWorkMode.TEST
  RequestDebugMode.STUB -> WktWorkMode.STUB
  null -> WktWorkMode.NONE
}

private fun Debug?.fromTransportToStubCase() = when (this?.stub) {
  RequestDebugStubs.SUCCESS -> WktStub.SUCCESS
  RequestDebugStubs.NOT_FOUND -> WktStub.NOT_FOUND
  RequestDebugStubs.BAD_ID -> WktStub.BAD_ID
  RequestDebugStubs.BAD_TITLE -> WktStub.BAD_TITLE
  RequestDebugStubs.BAD_DESCRIPTION -> WktStub.BAD_DESCRIPTION
  RequestDebugStubs.CANNOT_DELETE -> WktStub.CANNOT_DELETE
  RequestDebugStubs.EMPTY_SEARCH -> WktStub.EMPTY_SEARCH
  null -> WktStub.NONE
}