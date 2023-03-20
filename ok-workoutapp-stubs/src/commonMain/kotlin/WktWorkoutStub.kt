package ru.otus.otuskotlin.workoutapp.stubs

import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.stubs.WktWorkoutOwnWeightStub.WKT_WORKOUT
import ru.otus.otuskotlin.workoutapp.workout.common.models.*

object WktWorkoutStub {
  fun get(): WktWorkout = WKT_WORKOUT.copy()

  fun prepareSearchPayload(): WktWorkoutSearchPayload {
    return WktWorkoutSearchPayload(
      groups = mutableListOf(
        WktWorkoutSearchResult(
          groupName = WktWorkoutType.LEGS.name,
          workouts = prepareSearchListByType(WktWorkoutType.LEGS) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktWorkoutType.ARMS.name,
          workouts = prepareSearchListByType(WktWorkoutType.ARMS) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktWorkoutType.CORE.name,
          workouts = prepareSearchListByType(WktWorkoutType.CORE) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.OWN_WEIGHT.name,
          workouts = prepareSearchListByEquipment(WktEquipment.OWN_WEIGHT) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.DUMBBELLS.name,
          workouts = prepareSearchListByEquipment(WktEquipment.DUMBBELLS) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.BARS.name,
          workouts = prepareSearchListByEquipment(WktEquipment.BARS) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.HORIZONTAL_BAR.name,
          workouts = prepareSearchListByEquipment(WktEquipment.HORIZONTAL_BAR) as MutableList<WktWorkout>
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.JUMPING_ROPE.name,
          workouts = prepareSearchListByEquipment(WktEquipment.JUMPING_ROPE) as MutableList<WktWorkout>
        ),
      )
    )
  }

  fun prepareCreateWorkoutPayload(id: String, base: WktWorkout): WktWorkout = base.copy(
    id = WktWorkoutId(id)
  )


  private fun prepareSearchListByType(type: WktWorkoutType): List<WktWorkout> = listOf(
    wktWorkout(WKT_WORKOUT, id = "755", type = type, equipment = WktEquipment.OWN_WEIGHT),
    wktWorkout(WKT_WORKOUT, id = "783", type = type, equipment = WktEquipment.DUMBBELLS),
    wktWorkout(WKT_WORKOUT, id = "783", type = type, equipment = WktEquipment.BARS),
    wktWorkout(WKT_WORKOUT, id = "809", type = type, equipment = WktEquipment.HORIZONTAL_BAR),
    wktWorkout(WKT_WORKOUT, id = "809", type = type, equipment = WktEquipment.JUMPING_ROPE),
    wktWorkout(WKT_WORKOUT, id = "233", type = type, equipment = WktEquipment.OWN_WEIGHT),
  )

  private fun prepareSearchListByEquipment(equipment: WktEquipment): List<WktWorkout> = listOf(
    wktWorkout(WKT_WORKOUT, id = "755", type = WktWorkoutType.ARMS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "783", type = WktWorkoutType.LEGS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "783", type = WktWorkoutType.CORE, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "809", type = WktWorkoutType.ARMS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "809", type = WktWorkoutType.LEGS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "233", type = WktWorkoutType.CORE, equipment = equipment),
  )

  private fun wktWorkout(base: WktWorkout, id: String, type: WktWorkoutType, equipment: WktEquipment) = base.copy(
    id = WktWorkoutId(id),
    title = "Title $equipment $type $id",
    description = "Desc $equipment $type $id",
    type = type
  )
}