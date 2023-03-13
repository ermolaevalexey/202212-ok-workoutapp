package ru.otus.otuskotlin.workoutapp.feedback.common.models

import ru.otus.otuskotlin.workoutapp.common.models.WktUserId

data class WktFeedbackUser(
  var id: WktUserId = WktUserId.NONE,
  var name: String = "",
)