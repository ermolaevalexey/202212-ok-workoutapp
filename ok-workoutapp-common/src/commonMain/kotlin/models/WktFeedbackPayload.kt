package models

data class WktFeedbackPayload (
  var workout: WktWorkoutId = WktWorkoutId.NONE,
  var id: WktFeedbackId = WktFeedbackId.NONE,
  var review: String = "",
  var rating: Double = 0.0,
  var user: WktUserId? = WktUserId.NONE
)