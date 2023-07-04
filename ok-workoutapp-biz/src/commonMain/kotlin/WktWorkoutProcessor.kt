package ru.otus.otuskotlin.workoutapp.biz

import ru.otus.otuskotlin.workoutapp.biz.general.operation
import ru.otus.otuskotlin.workoutapp.biz.general.prepareResultWorkout
import ru.otus.otuskotlin.workoutapp.biz.general.stubs
import ru.otus.otuskotlin.workoutapp.biz.validation.*
import ru.otus.otuskotlin.workoutapp.biz.workers.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.cor.rootChain
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.workout.common.repo.DbWorkoutIdRequest
import ru.otus.otuskotlin.workoutapp.workout.common.repo.DbWorkoutRequest
import ru.otus.otuskotlin.workoutapp.workout.common.repo.DbWorkoutSearchRequest
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutCorSettings

class WktWorkoutProcessor(private val settings: WktWorkoutCorSettings = WktWorkoutCorSettings()) {
  suspend fun exec(ctx: WktWorkoutContext) = BusinessChainWorkout.exec(ctx.apply { settings = this@WktWorkoutProcessor.settings })

  companion object {
    private val BusinessChainWorkout = rootChain<WktWorkoutContext> {
      initStatus("Инициализация статуса")
      initRepo("Инициализация репозитория")
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
          finishValidationWorkout("Завершение проверок")
        }
        worker {
          title = "Создание тренировки"
          handle {
            val dbRes = workoutRepo.createWorkout(DbWorkoutRequest(workoutCreateRequest))
            val data = dbRes.data
            if (dbRes.isSuccess && data != null) {
              workoutCreateResponse = data
            } else {
              state = WktState.FAILING
              errors.addAll(dbRes.errors)
            }
          }
        }
        prepareResultWorkout("Подготовка ответа")
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
          finishValidationWorkout("Завершение проверок")
        }
        worker {
          title = "Чтение тренировки"
          handle {
            val dbRes = workoutRepo.readWorkout(DbWorkoutIdRequest(workoutReadRequest))
            val data = dbRes.data
            if (dbRes.isSuccess && data != null) {
              workoutReadResponse = data
            } else {
              state = WktState.FAILING
              errors.addAll(dbRes.errors)
            }
          }
        }
        prepareResultWorkout("Подготовка ответа")
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
          finishValidationWorkout("")
        }
        worker {
          title = "Обновление тренировки"
          handle {
            val dbRes = workoutRepo.updateWorkout(DbWorkoutRequest(workoutUpdateRequest))
            val data = dbRes.data
            if (dbRes.isSuccess && data != null) {
              workoutUpdateResponse = data
            } else {
              state = WktState.FAILING
              errors.addAll(dbRes.errors)
            }
          }
        }
        prepareResultWorkout("Подготовка ответа")
      }

      operation("Поиск тренировок", WktCommand.WORKOUT_SEARCH) {
        stubs("Обработка стабов") {
          stubSearchWorkoutSuccess("Обработка успешного поиска")
        }
        worker {
          title = "Поиск тренировок"
          handle {
            val dbRes = workoutRepo.searchWorkouts(DbWorkoutSearchRequest(workoutSearchRequest))
            val data = dbRes.data
            if (dbRes.isSuccess) {
              workoutSearchResponse = data
            } else {
              state = WktState.FAILING
              errors.addAll(dbRes.errors)
            }
          }
        }
        prepareResultWorkout("Подготовка ответа")
      }
    }.build()
  }
}