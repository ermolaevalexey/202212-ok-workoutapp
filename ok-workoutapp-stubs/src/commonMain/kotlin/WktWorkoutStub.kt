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
          workouts = prepareSearchListByType(WktWorkoutType.LEGS)
        ),
        WktWorkoutSearchResult(
          groupName = WktWorkoutType.ARMS.name,
          workouts = prepareSearchListByType(WktWorkoutType.ARMS)
        ),
        WktWorkoutSearchResult(
          groupName = WktWorkoutType.CORE.name,
          workouts = prepareSearchListByType(WktWorkoutType.CORE)
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.OWN_WEIGHT.name,
          workouts = prepareSearchListByEquipment(WktEquipment.OWN_WEIGHT)
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.DUMBBELLS.name,
          workouts = prepareSearchListByEquipment(WktEquipment.DUMBBELLS)
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.BARS.name,
          workouts = prepareSearchListByEquipment(WktEquipment.BARS)
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.HORIZONTAL_BAR.name,
          workouts = prepareSearchListByEquipment(WktEquipment.HORIZONTAL_BAR)
        ),
        WktWorkoutSearchResult(
          groupName = WktEquipment.JUMPING_ROPE.name,
          workouts = prepareSearchListByEquipment(WktEquipment.JUMPING_ROPE)
        ),
      )
    )
  }

  fun prepareCreateWorkoutPayload(id: String, base: WktWorkout): WktWorkout = base.copy(
    id = WktWorkoutId(id)
  )

  private fun prepareSearchListByType(type: WktWorkoutType): MutableMap<String, MutableList<WktWorkout>> = mutableListOf(
    wktWorkout(WKT_WORKOUT, id = "755", type = type, equipment = WktEquipment.OWN_WEIGHT),
    wktWorkout(WKT_WORKOUT, id = "783", type = type, equipment = WktEquipment.DUMBBELLS),
    wktWorkout(WKT_WORKOUT, id = "783", type = type, equipment = WktEquipment.BARS),
    wktWorkout(WKT_WORKOUT, id = "809", type = type, equipment = WktEquipment.HORIZONTAL_BAR),
    wktWorkout(WKT_WORKOUT, id = "809", type = type, equipment = WktEquipment.JUMPING_ROPE),
    wktWorkout(WKT_WORKOUT, id = "233", type = type, equipment = WktEquipment.OWN_WEIGHT),
  ).groupBy { it.type.toString() }.mapValues { it.value.toMutableList() }.toMutableMap()

  private fun prepareSearchListByEquipment(equipment: WktEquipment): MutableMap<String, MutableList<WktWorkout>>  = mutableListOf(
    wktWorkout(WKT_WORKOUT, id = "755", type = WktWorkoutType.ARMS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "783", type = WktWorkoutType.LEGS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "783", type = WktWorkoutType.CORE, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "809", type = WktWorkoutType.ARMS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "809", type = WktWorkoutType.LEGS, equipment = equipment),
    wktWorkout(WKT_WORKOUT, id = "233", type = WktWorkoutType.CORE, equipment = equipment),
  ).groupBy { it.equipment.toString() }.mapValues { it.value.toMutableList() }.toMutableMap()

  private fun wktWorkout(base: WktWorkout, id: String, type: WktWorkoutType, equipment: WktEquipment) = base.copy(
    id = WktWorkoutId(id),
    title = "Title $equipment $type $id",
    description = "Desc $equipment $type $id",
    type = type
  )
}