package models

data class WktWorkoutContent(
  var video: String = "",
  var steps: List<WktWorkoutStep>? = mutableListOf()
)
