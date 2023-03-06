package models

data class WktWorkoutSearchPayload (
  var groups: MutableList<WktWorkoutSearchResult> = mutableListOf()
)