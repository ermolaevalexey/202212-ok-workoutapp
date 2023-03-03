package models

data class WktWorkoutSearch (
  var groupBy: MutableList<WktWorkoutSearchGroupBy> = mutableListOf()
)