package ru.otus.otuskotlin.workoutapp.workout.app.ktor.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.*
import ru.otus.otuskotlin.workoutapp.mappers.v1.*
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.WktAppSettings


suspend fun ApplicationCall.createWorkout(appSettings: WktAppSettings) {
  val request = receive<WorkoutCreateRequest>()
  val ctx = WktWorkoutContext()
  val proc = appSettings.processorWorkout
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportWorkoutCreate())
}

suspend fun ApplicationCall.readWorkout(appSettings: WktAppSettings) {
  val request = receive<WorkoutReadRequest>()
  val ctx = WktWorkoutContext()
  val proc = appSettings.processorWorkout
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportWorkoutRead())
}

suspend fun ApplicationCall.updateWorkout(appSettings: WktAppSettings) {
  val request = receive<WorkoutUpdateRequest>()
  val ctx = WktWorkoutContext()
  val proc = appSettings.processorWorkout
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportWorkoutUpdate())
}

suspend fun ApplicationCall.searchWorkouts(appSettings: WktAppSettings) {
  val request = receive<WorkoutSearchRequest>()
  val ctx = WktWorkoutContext()
  val proc = appSettings.processorWorkout
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportWorkoutSearch())
}