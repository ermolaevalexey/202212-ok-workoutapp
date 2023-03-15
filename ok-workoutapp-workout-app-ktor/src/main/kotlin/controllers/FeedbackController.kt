package ok.workoutapp.workout.app.ktor.controllers

import WktFeedbackContext
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.workoutapp.api.v1.models.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.mappers.v1.*
import ru.otus.otuskotlin.workoutapp.stubs.*


suspend fun ApplicationCall.createFeedback() {
  val request = receive<FeedbackCreateRequest>()
  val ctx = WktFeedbackContext()
  ctx.fromTransport(request)
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
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
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}

suspend fun ApplicationCall.deleteFeedback() {
  val request = receive<FeedbackDeleteRequest>()
  val ctx = WktFeedbackContext()
  ctx.fromTransport(request)
  ctx.feedbackDeleteResponse = WktFeedbackStub.prepareDeleteFeedback(
    ctx.feedbackDeleteRequest.id.asString(),
    ctx.feedbackDeleteRequest.user?.asString()
  )
  ctx.state = WktState.RUNNING
  respond(ctx.toTransport())
}