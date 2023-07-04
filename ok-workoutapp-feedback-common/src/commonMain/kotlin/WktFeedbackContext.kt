package ru.otus.otuskotlin.workoutapp.feedback.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.workoutapp.common.constants.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.feedback.common.models.*
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.IFeedbackRepository

data class WktFeedbackContext(
  override var command: WktCommand = WktCommand.NONE,
  override var state: WktState = WktState.NONE,
  override var errors: MutableList<WktError> = mutableListOf(),

  var workMode: WktWorkMode = WktWorkMode.PROD,
  override var stubCase: WktStub = WktStub.NONE,

  var requestId: WktRequestId = WktRequestId.NONE,
  var timeStart: Instant = Instant.NONE,
  var settings: WktFeedbackCorSettings = WktFeedbackCorSettings.NONE,
  var feedbackRepo: IFeedbackRepository = IFeedbackRepository.NONE,
  var feedbackValidity: WktFeedbackPayload = WktFeedbackPayload(),
  var feedbackValid: WktFeedbackPayload = WktFeedbackPayload(),

  var feedbackCreateRequest: WktFeedbackPayload = WktFeedbackPayload(),
  var feedbackUpdateRequest: WktFeedbackPayload = WktFeedbackPayload(),
  var feedbackReadRequest: WktWorkoutId = WktWorkoutId.NONE,
  var feedbackDeleteRequest: WktFeedbackPayload = WktFeedbackPayload(),

  var feedbackCreateResponse: WktFeedback = WktFeedback(),
  var feedbackReadResponse: MutableList<WktFeedback> = mutableListOf(),
  var feedbackUpdateResponse: WktFeedback = WktFeedback(),
  var feedbackDeleteResponse: WktFeedbackPayload = WktFeedbackPayload(),

  var feedbackResponse: Any = WktFeedback()

): WktContext