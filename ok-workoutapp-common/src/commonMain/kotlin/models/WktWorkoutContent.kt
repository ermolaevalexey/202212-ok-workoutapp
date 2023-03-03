package models

data class WktWorkoutContent(
  var video: String = "",
  var steps: MutableList<WktWorkoutStep> = mutableListOf()
)
