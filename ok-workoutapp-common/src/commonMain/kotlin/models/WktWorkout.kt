package models

data class WktWorkout (
  var id: WktWorkoutId = WktWorkoutId.NONE,
  var title: String = "",
  var description: String = "",
  var type: WktWorkoutType = WktWorkoutType.NONE,
  var equipment: WktEquipment = WktEquipment.NONE,
  var content: WktWorkoutContent = WktWorkoutContent(),
  var feedback: MutableList<WktFeedback> = mutableListOf(),
  var rating: Double = 0.0
)