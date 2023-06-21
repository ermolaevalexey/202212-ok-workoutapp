package ru.otus.otuskotlin.workoutapp.repoInMemory.models

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackUser

class FeedbackEntity(
  var id: WktFeedbackId = WktFeedbackId.NONE,
  var workout: WktWorkoutId = WktWorkoutId.NONE,
  var review: String = "",
  var rating: Double = 0.0,
  var user: WktFeedbackUser? = null
) {
  constructor(model: WktFeedback): this(
    id = model.id.takeIf { it.asString().isNotBlank() } ?: WktFeedbackId.NONE,
    workout = model.workout.takeIf { it.asString().isNotBlank() } ?: WktWorkoutId.NONE,
    review = model.review.takeIf { it.isNotBlank() } ?: "",
    rating = model.rating ?: 0.0,
    user = model.user ?: WktFeedbackUser()
  )

  fun toInternal() = WktFeedback(
    id = id?.let { WktFeedbackId(it.asString()) } ?: WktFeedbackId.NONE,
    workout = workout?.let { WktWorkoutId(it.asString()) } ?: WktWorkoutId.NONE,
    review = review ?: "",
    rating = rating ?: 0.0
  )
}