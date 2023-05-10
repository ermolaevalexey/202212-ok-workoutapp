package ru.otus.otuskotlin.workoutapp.biz

import ru.otus.otuskotlin.workoutapp.biz.general.operation
import ru.otus.otuskotlin.workoutapp.biz.general.prepareResult
import ru.otus.otuskotlin.workoutapp.biz.general.stubs
import ru.otus.otuskotlin.workoutapp.biz.validation.*
import ru.otus.otuskotlin.workoutapp.biz.workers.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.cor.rootChain
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.stubs.WktWorkoutOwnWeightStub
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

class WktWorkoutProcessor {
  suspend fun exec(ctx: WktWorkoutContext) = BusinessChainWorkout.exec(ctx)

  companion object {
    private val BusinessChainWorkout = rootChain<WktWorkoutContext> {
      initStatus("Инициализация статуса")

      operation("Создание тренировки", WktCommand.WORKOUT_CREATE) {
        stubs("Обработка стабов") {
          stubCreateWorkoutSuccess("Обработка успешного создания")
          stubValidationWorkoutBadTitle("Имитация ошибки валидации названия")
          stubValidationWorkoutBadDescription("Имитация ошибки валидации описарния")
          stubReadWorkoutReadNotFound("Имитация не найденной тренировки - 404")
        }
        validation {
          worker("Копируем поля в объект валидации") {
            workoutValidity = workoutCreateRequest.copy()
          }
          worker("Очистка title") {
            workoutValidity.title = "".trim()
          }
          worker("Очистка description") {
            workoutValidity.description = "".trim()
          }
          validateTitleHasContent("Проверка, что название не пустое")
          validateDescriptionHasContent("Проверка, что описание не пустое")
          finishValidation("Завершение проверок")
        }
        prepareResult("Подготовка ответа")
      }

      operation("Получение тренировки", WktCommand.WORKOUT_READ) {
        stubs("Обработка стабов") {
          stubReadWorkoutSuccess("Обработка успешного чтения")
          stubValidationWorkoutBadId("Имитация ошибки плохого id")
        }
        validation {
          worker("Копируем поля в объект валидации") {
            workoutValidity.id = WktWorkoutId(workoutReadRequest.asString())
          }
          worker("Очистка id") {
            workoutValidity.id = WktWorkoutId.NONE
          }
          validateWorkoutIdExist("Проверка на существование id")
          finishValidation("Завершение проверок")
        }
        prepareResult("Подготовка ответа")
      }

      operation("Обновление тренировки", WktCommand.WORKOUT_UPDATE) {
        stubs("Обработка стабов") {
          stubUpdateWorkoutSuccess("Обработка успешного чтения")
          stubValidationWorkoutBadId("Имитация ошибки плохого id")
        }
        validation {
          worker("Копируем поля в объект валидации") {
            workoutValidity.id = WktWorkoutId(workoutUpdateRequest.id.asString())
          }
          worker("Очистка id") {
            workoutValidity.id = WktWorkoutId.NONE
          }
          validateWorkoutIdExist("Проверка на существование id")
          finishValidation("")
        }
        prepareResult("Подготовка ответа")
      }

      operation("Поиск тренировок", WktCommand.WORKOUT_SEARCH) {
        stubs("Обработка стабов") {
          stubSearchWorkoutSuccess("Обработка успешного поиска")
        }
        prepareResult("Подготовка ответа")
      }
    }.build()
  }
}