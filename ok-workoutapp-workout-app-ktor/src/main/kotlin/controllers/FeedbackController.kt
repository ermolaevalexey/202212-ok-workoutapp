package ru.otus.otuskotlin.workoutapp.workout.app.ktor.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.mappers.v1.*
import ru.otus.otuskotlin.workoutapp.stubs.*
import ru.otus.otuskotlin.workoutapp.workout.app.ktor.WktAppSettings


suspend fun ApplicationCall.createFeedback(appSettings: WktAppSettings) {
  val request = receive<FeedbackCreateRequest>()
  val ctx = WktFeedbackContext()
  val proc = appSettings.processorFeedback
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportFeedbackCreate())
}

suspend fun ApplicationCall.readFeedback(appSettings: WktAppSettings) {
  val request = receive<FeedbackReadRequest>()
  val ctx = WktFeedbackContext()
  val proc = appSettings.processorFeedback
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportFeedbackRead())
}

suspend fun ApplicationCall.updateFeedback(appSettings: WktAppSettings) {
  val request = receive<FeedbackUpdateRequest>()
  val ctx = WktFeedbackContext()
  val proc = appSettings.processorFeedback
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportFeedbackUpdate())
}

suspend fun ApplicationCall.deleteFeedback(appSettings: WktAppSettings) {
  val request = receive<FeedbackDeleteRequest>()
  val ctx = WktFeedbackContext()
  val proc = appSettings.processorFeedback
  ctx.fromTransport(request)
  proc.exec(ctx)
  respond(ctx.toTransportFeedbackDelete())
}