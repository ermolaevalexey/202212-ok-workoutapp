package ru.otus.otuskotlin.workoutapp.mappers.v1

import ru.otus.otuskotlin.workoutapp.mappers.v1.exceptions.UnknownWktCommand
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext
import ru.otus.otuskotlin.workoutapp.workout.common.models.*

fun WktWorkoutContext.toTransport(): Response = when (val cmd = command) {
  WktCommand.WORKOUT_SEARCH -> toTransportWorkoutSearch()
  WktCommand.WORKOUT_CREATE -> toTransportWorkoutCreate()
  WktCommand.WORKOUT_READ -> toTransportWorkoutRead()
  WktCommand.WORKOUT_UPDATE -> toTransportWorkoutUpdate()
  else -> throw UnknownWktCommand(cmd)
}


fun WktWorkoutContext.toTransportWorkoutSearch() = WorkoutSearchResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.FAILING) Result.ERROR else Result.SUCCESS,
  errors = errors.toTransportErrors(),
  workout = this.workoutSearchResponse.toTransport()
)
fun WktWorkoutContext.toTransportWorkoutCreate() = WorkoutCreateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.FAILING) Result.ERROR else Result.SUCCESS,
  errors = errors.toTransportErrors(),
  workout = this.workoutCreateResponse.toTransportWorkout()
)
fun WktWorkoutContext.toTransportWorkoutRead() = WorkoutReadResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.FAILING) Result.ERROR else Result.SUCCESS,
  errors = errors.toTransportErrors(),
  workout = this.workoutReadResponse.toTransportWorkout()
)
fun WktWorkoutContext.toTransportWorkoutUpdate() = WorkoutUpdateResponse(
  requestId = this.requestId.asString().takeIf { it.isNotBlank() },
  result = if (this.state == WktState.FAILING) Result.ERROR else Result.SUCCESS,
  errors = errors.toTransportErrors(),
  workout = this.workoutUpdateResponse.toTransportWorkout()
)

private fun WktWorkout.toTransportWorkout() = Workout(
  id = this.id.toTransport(),
  title = this.title,
  description = this.description,
  type = this.type.toTransport(),
  equipment = this.equipment.toTransport(),
  content = this.content.toTransport(),
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

private fun WktWorkoutSearchPayload.toTransport() = WorkoutSearchResponsePayload(
  groups = this.groups.map { it.toTransport() }
)
private fun WktWorkoutSearchResult.toTransport() = WorkoutSearchResponsePayloadGroupsInner(
  groupName = this.groupName,
  workouts = this.workouts.mapValues { it.value.map { wkt -> wkt.toTransportWorkout() }.toList() }
)