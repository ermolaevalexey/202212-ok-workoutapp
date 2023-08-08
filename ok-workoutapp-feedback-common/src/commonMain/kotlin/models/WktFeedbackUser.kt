package ru.otus.otuskotlin.workoutapp.feedback.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktUserId
import ru.otus.otuskotlin.workoutapp.common.models.WktUserRole

data class WktFeedbackUser(
  var id: WktUserId = WktUserId.NONE,
  var name: String = "",
  var role: WktUserRole = WktUserRole.USER
)