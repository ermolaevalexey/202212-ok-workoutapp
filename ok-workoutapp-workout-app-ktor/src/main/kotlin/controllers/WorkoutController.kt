package ok.workoutapp.workout.app.ktor.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.*
import ru.otus.otuskotlin.workoutapp.mappers.v1.*
import ru.otus.otuskotlin.workoutapp.stubs.*


suspend fun ApplicationCall.createWorkout() {
  val request = receive<WorkoutCreateRequest>()
  val ctx = WktWorkoutContext()
  ctx.fromTransport(request)
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