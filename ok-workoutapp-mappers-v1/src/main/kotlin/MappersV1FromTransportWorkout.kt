package ru.otus.otuskotlin.workoutapp.mappers.v1

import ru.otus.otuskotlin.workoutapp.mappers.v1.exceptions.UnknownRequestClass
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

fun WktWorkoutContext.fromTransport(request: Request) = when (request) {
  is WorkoutCreateRequest -> fromTransport(request)
  is WorkoutReadRequest -> fromTransport(request)
  is WorkoutUpdateRequest -> fromTransport(request)
  is WorkoutSearchRequest -> fromTransport(request)
  else -> throw UnknownRequestClass(request.javaClass)
}

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
fun WktWorkoutContext.fromTransport(request: WorkoutCreateRequest) {
  command = WktCommand.WORKOUT_CREATE
  requestId = request.requestId()
  workoutCreateRequest = request.workout.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktWorkoutContext.fromTransport(request: WorkoutReadRequest) {
  command = WktCommand.WORKOUT_READ
  requestId = request.requestId()
  workoutReadRequest = request.workout?.id.toWktWorkoutId()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktWorkoutContext.fromTransport(request: WorkoutUpdateRequest) {
  command = WktCommand.WORKOUT_UPDATE
  requestId = request.requestId()
  workoutUpdateRequest = request.workout.toInternal()
  workMode = request.debug?.fromTransportToWorkMode() ?: WktWorkMode.NONE
  stubCase = request.debug?.fromTransportToStubCase() ?: WktStub.NONE
}

fun WktWorkoutContext.fromTransport(request: WorkoutSearchRequest) {
  command = WktCommand.WORKOUT_SEARCH
  requestId = request.requestId()
  workoutSearchRequest = request.params.toInternal()
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
  title = this?.title ?: "",
  description = this?.description ?: "",
  type = this?.type?.fromTransport() ?: WktWorkoutType.NONE,
  equipment = this?.equipment?.fromTransport() ?: WktEquipment.NONE,
  content = this?.content?.toInternal() ?: WktWorkoutContent(),
  rating = this?.rating ?: 0.0
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