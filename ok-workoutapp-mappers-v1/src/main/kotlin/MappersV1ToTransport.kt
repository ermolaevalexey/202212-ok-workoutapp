import models.*
import ru.otus.otuskotlin.workoutapp.mappers.v1.exceptions.UnknownWktCommand
import ru.otus.otuskotlin.workoutapp.api.v1.models.*

fun WktContext.toTransport(): Response = when (val cmd = command) {
  WktCommand.FEEDBACK_CREATE -> toTransportFeedbackCreate()
  WktCommand.FEEDBACK_READ -> toTransportFeedbackRead()
  WktCommand.FEEDBACK_UPDATE -> toTransportFeedbackUpdate()
  WktCommand.FEEDBACK_DELETE -> toTransportFeedbackDelete()
  WktCommand.WORKOUT_SEARCH -> toTransportWorkoutSearch()
  WktCommand.WORKOUT_CREATE -> toTransportWorkoutCreate()
  WktCommand.WORKOUT_READ -> toTransportWorkoutRead()
  WktCommand.WORKOUT_UPDATE -> toTransportWorkoutUpdate()
  WktCommand.NONE -> throw UnknownWktCommand(cmd)
}

fun WktContext.toTransportFeedbackCreate() = FeedbackCreateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackCreateResponse.toTransportFeedback()
)
fun WktContext.toTransportFeedbackRead() = FeedbackReadResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackReadResponse.map { it.toTransportFeedback() }
)
fun WktContext.toTransportFeedbackUpdate() = FeedbackUpdateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackUpdateResponse.toTransportFeedback()
)
fun WktContext.toTransportFeedbackDelete() = FeedbackDeleteResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  feedback = this.feedbackDeleteResponse.toTransport()
)
fun WktContext.toTransportWorkoutSearch() = WorkoutSearchResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  workout = this.workoutSearchResponse.toTransport()
)
fun WktContext.toTransportWorkoutCreate() = WorkoutCreateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  workout = this.workoutCreateResponse.toTransportWorkout()
)
fun WktContext.toTransportWorkoutRead() = WorkoutReadResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  workout = this.workoutReadResponse.toTransportWorkout()
)
fun WktContext.toTransportWorkoutUpdate() = WorkoutUpdateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (state == WktState.RUNNING) Result.SUCCESS else Result.ERROR,
  errors = errors.toTransportErrors(),
  workout = this.workoutUpdateResponse.toTransportWorkout()
)

private fun WktWorkout.toTransportWorkout() = Workout(
  id = this.id.toString(),
  title = this.title,
  description = this.description,
  type = this.type.toTransport(),
  equipment = this.equipment.toTransport(),
  content = this.content.toTransport(),
  feedback = this.feedback.map { it.toTransportFeedback() },
  rating = this.rating
)

private fun WktWorkoutType.toTransport() = when (this) {
  WktWorkoutType.CORE -> WorkoutType.CORE
  WktWorkoutType.ARMS -> WorkoutType.ARMS
  WktWorkoutType.LEGS -> WorkoutType.LEGS
  else -> null
}

private fun WktEquipment.toTransport() = when (this) {
  WktEquipment.OWN_WEIGHT -> Equipment.OWN_WEIGHT
  WktEquipment.BARS -> Equipment.BARS
  WktEquipment.DUMBBELLS -> Equipment.DUMBBELLS
  WktEquipment.HORIZONTAL_BAR -> Equipment.HORIZONTAL_BAR
  WktEquipment.JUMPING_ROPE -> Equipment.JUMPING_ROPE
  else -> null
}

private fun WktWorkoutContent.toTransport() = WorkoutBaseContent(
  video = this.video,
  steps = this.steps?.map { it.toTransport() }
)

private fun WktWorkoutStep.toTransport() = WorkoutStep(
  title = this.title,
  description = this.description,
  timecode = this.timeCode
)

private fun WktFeedback.toTransportFeedback() = Feedback(
  id = this.id.toString(),
  review = this.review,
  rating = this.rating,
  user = this.user?.toTransport()
)

private fun WktFeedbackUser.toTransport() = FeedbackUser(
  id = this.id.toString(),
  name = this.name
)

private fun WktFeedbackPayload.toTransport() = FeedbackDeleteResponsePayload(
  id = this.id.toString(),
)

private fun WktWorkoutSearchPayload.toTransport() = WorkoutSearchResponsePayload(
  groups = this.groups.map { it.toTransport() }
)
private fun WktWorkoutSearchResult.toTransport() = WorkoutSearchResponsePayloadGroupsInner(
  groupName = this.groupName,
  workouts = this.workouts.map { it.toTransportWorkout() }
)

private fun List<WktError>.toTransportErrors(): List<Error>? = this
  .map { it.toTransport() }
  .toList()
  .takeIf { it.isNotEmpty() }

private fun WktError.toTransport() = Error(
  code = code.takeIf { it.isNotBlank() },
  group = group.takeIf { it.isNotBlank() },
  field = field.takeIf { it.isNotBlank() },
  message = message.takeIf { it.isNotBlank() },
)