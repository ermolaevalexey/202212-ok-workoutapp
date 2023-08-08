package ru.otus.otuskotlin.workoutapp.feedback.common.repo

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback

data class DbFeedbackIdRequest(
  val id: WktFeedbackId
) {
  constructor(fbk: WktFeedback): this(fbk.id)
}