package ru.otus.otuskotlin.workoutapp.stubs
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.workout.common.models.*

object WktWorkoutOwnWeightStub {
  val WKT_WORKOUT: WktWorkout
    get() = WktWorkout(
      id = WktWorkoutId("777"),
      title = "Рельефные руки",
      description = "Самые эффективные упражнения на руки без оборудования",
      type = WktWorkoutType.ARMS,
      equipment = WktEquipment.OWN_WEIGHT,
      content = WktWorkoutContent(
        video = "https://some-training/777",
        steps = mutableListOf(
          WktWorkoutStep(
            title = "Поднимите руки",
            description = "Рельефные руки – это красиво",
            timeCode = "00:10"
          ),
          WktWorkoutStep(
            title = "Опустите руки",
            description = "Рельефные руки – это красиво",
            timeCode = "00:50"
          ),
          WktWorkoutStep(
            title = "Снова поднимите руки",
            description = "Рельефные руки – это красиво",
            timeCode = "05:25"
          )
        )
      )
    )

  val WKT_WORKOUT_DUMBBELLS = WKT_WORKOUT.copy(
    id = WktWorkoutId("128"),
    title = "Тренировка на плечи",
    description = "Просто подвигать плечами",
    type = WktWorkoutType.ARMS,
    equipment = WktEquipment.DUMBBELLS,
    content = WktWorkoutContent(
      video = "https://some-static/video/123",
      steps = mutableListOf(
        WktWorkoutStep(title = "Поднимите вверх плечи", description = "Это важно", timeCode = "00:15"),
        WktWorkoutStep(title = "Опустите вниз плечи", description = "Еще более важно", timeCode = "00:30"),
        WktWorkoutStep(title = "Cнова поднимите вверх плечи", description = "Архиважно", timeCode = "01:17")
      )
    )
  )
}