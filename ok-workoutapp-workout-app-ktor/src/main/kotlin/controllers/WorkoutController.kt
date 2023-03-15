package ok.workoutapp.workout.app.ktor.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.*
import ru.otus.otuskotlin.workoutapp.mappers.v1.*
import ru.otus.otuskotlin.workoutapp.stubs.*
import ru.otus.otuskotlin.workoutapp.workout.common.models.WktWorkoutType


suspend fun ApplicationCall.createWorkout() {
  val request = receive<WorkoutCreateRequest>()
  val ctx = WktWorkoutContext()
  ctx.fromTransport(request)
  ctx.workoutCreateResponse = WktWorkoutStub.prepareCreateWorkoutPayload(
    "555",
    ctx.workoutCreateRequest.copy()
  )
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}

suspend fun ApplicationCall.readWorkout() {
  val request = receive<WorkoutReadRequest>()
  val ctx = WktWorkoutContext()
  ctx.fromTransport(request)
  ctx.workoutReadResponse = WktWorkoutStub.get()
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}

suspend fun ApplicationCall.updateWorkout() {
  val request = receive<WorkoutUpdateRequest>()
  val ctx = WktWorkoutContext()
  ctx.fromTransport(request)
  println(ctx.workoutUpdateRequest)
  ctx.workoutUpdateResponse = WktWorkoutStub.get().copy(
    title = ctx.workoutUpdateRequest.title.takeIf { it.isNotBlank() } ?: WktWorkoutStub.get().title,
    description = ctx.workoutUpdateRequest.description.takeIf { it.isNotBlank() } ?: WktWorkoutStub.get().description,
    content = ctx.workoutUpdateRequest.content.takeIf { it.video.isNotEmpty() || !it.steps.isNullOrEmpty() } ?: WktWorkoutStub.get().content
  )
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}

suspend fun ApplicationCall.searchWorkouts() {
  val request = receive<WorkoutSearchRequest>()
  val ctx = WktWorkoutContext()
  ctx.fromTransport(request)
  ctx.workoutSearchResponse = WktWorkoutStub.prepareSearchPayload()
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}