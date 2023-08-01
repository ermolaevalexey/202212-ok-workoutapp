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
//  ctx.fromTransport(request)
//  ctx.feedbackCreateResponse = WktFeedbackStub.prepareCreateFeedback(
//    id = "777",
//    workoutId = ctx.feedbackCreateRequest.workout.asString(),
//    userId = ctx.feedbackCreateRequest.user?.asString(),
//    review = ctx.feedbackCreateRequest.review,
//    rating = ctx.feedbackCreateRequest.rating
//  )
//  ctx.state = WktState.RUNNING
  respond(ctx.toTransportFeedbackCreate())
}

suspend fun ApplicationCall.readFeedback() {
  val request = receive<FeedbackReadRequest>()
  val ctx = WktFeedbackContext()
  ctx.fromTransport(request)
  ctx.feedbackReadResponse = WktFeedbackStub.prepareFeedbackList(ctx.feedbackReadRequest.asString()) as MutableList<WktFeedback>
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}

suspend fun ApplicationCall.updateFeedback() {
  val request = receive<FeedbackUpdateRequest>()
  val ctx = WktFeedbackContext()
  ctx.fromTransport(request)
  ctx.feedbackUpdateResponse = WktFeedbackStub.prepareUpdateFeedback(
    review = ctx.feedbackUpdateRequest.data.review,
    rating = ctx.feedbackUpdateRequest.data.rating
  )
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}

suspend fun ApplicationCall.deleteFeedback() {
  val request = receive<FeedbackDeleteRequest>()
  val ctx = WktFeedbackContext()
  ctx.fromTransport(request)
  ctx.feedbackDeleteResponse = WktFeedbackStub.prepareDeleteFeedback(
    workoutId = WktFeedbackStub.WKT_FEEDBACK.workout.asString(),
    userId = ctx.feedbackDeleteRequest.userId.asString(),
    feedbackId = WktFeedbackStub.WKT_FEEDBACK.id.asString()
  )
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}