package models

data class WktFeedback(
  var review: String = "",
  var rating: Double = 0.0,
  var user: WktFeedbackUser? = null
)