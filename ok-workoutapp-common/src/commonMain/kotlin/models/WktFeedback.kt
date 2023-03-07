package models

data class WktFeedback(
  var id: WktFeedbackId = WktFeedbackId.NONE,
  var workout: WktWorkoutId = WktWorkoutId.NONE,
  var review: String = "",
  var rating: Double = 0.0,
  var user: WktFeedbackUser? = null
)