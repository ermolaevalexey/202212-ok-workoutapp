package ru.otus.otuskotlin.workoutapp.stubs

import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktUserId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedback
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackUser

object WktFeedbackStub {
  val WKT_FEEDBACK: WktFeedback
    get() = WktFeedback(
      id = WktFeedbackId("128"),
      workout = WktWorkoutId("wkt-185"),
      review = "Тяжеловато!",
      user = WktFeedbackUser(
        name = "Серсея Ланнистер",
        id = WktUserId("usr345")
      ),
      rating = 2.3
    )

  fun prepareFeedbackList(workoutId: String): List<WktFeedback> = listOf(
    prepareFeedback("123", workoutId, "user567", 2.3),
    prepareFeedback("456", workoutId, "user788", 3.0),
    prepareFeedback("789", workoutId, "user2333", 4.7),
    prepareFeedback("885", workoutId, "user567", 5.0),
    prepareFeedback("344", workoutId, "user567", 2.1),
    prepareFeedback("2445", workoutId, "user567", 3.3)
  )

  fun prepareCreateFeedback(id: String, workoutId: String, userId: String?, rating: Double, review: String): WktFeedback = prepareFeedback(
    id, workoutId, userId, rating
  ).copy(review = review)


  fun prepareUpdateFeedback(review: String?, rating: Double?): WktFeedback = WKT_FEEDBACK.copy(
    review = review ?: WKT_FEEDBACK.review,
    rating = rating ?: WKT_FEEDBACK.rating
  )

  fun prepareDeleteFeedback(workoutId: String, userId: String?, feedbackId: String): WktFeedbackPayload = WktFeedbackPayload(
    id = WktFeedbackId(feedbackId),
    workout = WktWorkoutId(workoutId),
    user = if (userId is String) WktUserId(userId) else WktUserId.NONE
  )

  private fun prepareFeedback(id: String, workoutId: String, userId: String?, rating: Double): WktFeedback = WKT_FEEDBACK.copy(
    id = WktFeedbackId(id),
    workout = WktWorkoutId(workoutId),
    review = "Review $id $workoutId $userId",
    user = WktFeedbackUser(
      name = "User $userId",
      id = if (userId is String) WktUserId(userId) else WktUserId.NONE
    ),
    rating = rating
  )
}