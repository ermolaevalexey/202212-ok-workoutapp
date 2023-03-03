package models

data class WktWorkoutSearchResult(
  var groupName: String = "",
  var workouts: MutableList<WktWorkout> = mutableListOf()
)