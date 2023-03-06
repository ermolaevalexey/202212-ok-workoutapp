package models

data class WktWorkoutSearch(
  var groupBy: List<WktWorkoutSearchGroupBy> = mutableListOf()
)