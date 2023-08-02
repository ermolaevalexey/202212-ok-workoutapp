package ru.otus.otuskotlin.workoutapp.biz

import ru.otus.otuskotlin.workoutapp.biz.general.operation
import ru.otus.otuskotlin.workoutapp.biz.general.prepareResultFeedback
import ru.otus.otuskotlin.workoutapp.biz.general.stubs
import ru.otus.otuskotlin.workoutapp.biz.validation.*
import ru.otus.otuskotlin.workoutapp.biz.workers.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktState
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.cor.rootChain
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackCorSettings
import ru.otus.otuskotlin.workoutapp.feedback.common.models.WktFeedbackPayload
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.DbFeedbackCreateRequest
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.DbFeedbackIdWorkoutIdRequest
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.DbFeedbackUpdateRequest
import ru.otus.otuskotlin.workoutapp.feedback.common.repo.DbFeedbackWorkoutIdRequest

class WktFeedbackProcessor(private val settings: WktFeedbackCorSettings = WktFeedbackCorSettings()) {
  suspend fun exec(ctx: WktFeedbackContext) = BusinessChainFeedback.exec(ctx.apply { settings = this@WktFeedbackProcessor.settings })

  private val BusinessChainFeedback = rootChain<WktFeedbackContext> {
    initStatus("Инициализация статуса")
    initRepo("Инициализация репозитория")
    operation("Создание отзыва", WktCommand.FEEDBACK_CREATE) {
      stubs("Обработка стабов") {
        stubCreateFeedbackSuccess("Обработка успешного создания")
        stubValidationFeedbackEmptyReview("Имитация ошибки валидации названия")
      }
      validation {
        worker("Копируем поля в объект валидации") {
          feedbackValidity = feedbackCreateRequest.data.copy()
        }
        validateReviewIsExist("Проверка, что отзыв не пуст")
        finishValidationFeedback("Завершение проверок")
      }
      worker {
        title = "Создание отзыва"
        handle {
          val dbRes = feedbackRepo.createFeedback(DbFeedbackCreateRequest(
            workoutId = feedbackCreateRequest.workoutId,
            data = feedbackCreateRequest.data
          ))
          val data = dbRes.data
          if (dbRes.isSuccess && data != null) {
            state = WktState.RUNNING
            feedbackCreateResponse = data
          } else {
            state = WktState.FAILING
            errors.addAll(dbRes.errors)
          }
        }
      }
      prepareResultFeedback("Подготовка ответа")
    }

    operation("Получение отзывов", WktCommand.FEEDBACK_READ) {
      stubs("Обработка стабов") {
        stubReadFeedbackSuccess("Обработка успешного чтения")
        stubValidationFeedbackBadWorkoutId("Имитация ошибки плохого id тренировки")
      }
      validation {
        worker("Копируем поля в объект валидации") {
          feedbackValidity = WktFeedbackPayload(
            workoutId = feedbackReadRequest
          )
        }
        validateWorkoutId("Проверка на существование поля workoutId")
        finishValidationFeedback("Завершение проверок")
      }
      worker {
        title = "Получение отзывов"
        handle {
          val dbRes = feedbackRepo.readFeedback(
            DbFeedbackWorkoutIdRequest(
              workoutId = feedbackReadRequest,
            )
          )
          val data = dbRes.data
          if (dbRes.isSuccess) {
            state = WktState.RUNNING
            feedbackReadResponse = data.toMutableList()
          } else {
            state = WktState.FAILING
            errors.addAll(dbRes.errors)
          }
        }
      }
      prepareResultFeedback("Подготовка ответа")
    }

    operation("Обновление отзыва", WktCommand.FEEDBACK_UPDATE) {
      stubs("Обработка стабов") {
        stubCreateFeedbackSuccess("Обработка успешного чтения")
        stubValidationFeedbackBadWorkoutId("Имитация ошибки плохого id тренировки")
        stubValidationFeedbackEmptyReview("Имитация ошибки плохого поля review")
      }
      validation {
        worker("Копируем поля в объект валидации") {
          feedbackValidity = feedbackUpdateRequest.data.copy()
        }
        validateReviewIsExist("Проверка на существование review")
        finishValidationFeedback("Завершение проверок")
      }
      worker {
        title = "Обновление отзыва"
        handle {
          val dbRes = feedbackRepo.updateFeedback(DbFeedbackUpdateRequest(
            feedbackId = feedbackUpdateRequest.feedbackId,
            workoutId = feedbackUpdateRequest.workoutId,
            data = feedbackUpdateRequest.data
          ))
          val data = dbRes.data
          if (dbRes.isSuccess && data != null) {
            state = WktState.RUNNING
            feedbackUpdateResponse = data
          } else {
            state = WktState.FAILING
            errors.addAll(dbRes.errors)
          }
        }
      }
      prepareResultFeedback("Подготовка ответа")
    }

    operation("Поиск тренировок", WktCommand.FEEDBACK_DELETE) {
      stubs("Обработка стабов") {
        stubFeedbackDeleteSuccess("Обработка успешного поиска")
        stubFeedbackDeleteFail("Имитация ошибки удаления отзыва")
      }
      validation {
        validateFeedbackIdIsExist("проверка на передачу id отзыва в запросе")
        finishValidationFeedback("Завершение проверок")
      }
      prepareResultFeedback("Подготовка ответа")
    }
  }.build()
}