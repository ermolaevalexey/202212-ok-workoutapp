package ru.otus.otuskotlin.workoutapp.biz

import ru.otus.otuskotlin.workoutapp.biz.general.operation
import ru.otus.otuskotlin.workoutapp.biz.general.prepareResult
import ru.otus.otuskotlin.workoutapp.biz.general.stubs
import ru.otus.otuskotlin.workoutapp.biz.validation.*
import ru.otus.otuskotlin.workoutapp.biz.workers.*
import ru.otus.otuskotlin.workoutapp.common.models.WktCommand
import ru.otus.otuskotlin.workoutapp.common.models.WktFeedbackId
import ru.otus.otuskotlin.workoutapp.common.models.WktWorkoutId
import ru.otus.otuskotlin.workoutapp.cor.rootChain
import ru.otus.otuskotlin.workoutapp.cor.worker
import ru.otus.otuskotlin.workoutapp.feedback.common.WktFeedbackContext

class WktFeedbackProcessor {
  suspend fun exec(ctx: WktFeedbackContext) = BusinessChainFeedback.exec(ctx)

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
          feedbackValidity = feedbackCreateRequest.copy()
        }
        worker("Очистка review") {
          feedbackValidity.review = "".trim()
        }
        validateReviewIsExist("Проверка, что отзыв не пуст")
        finishValidation("Завершение проверок")
      }
      prepareResult("Подготовка ответа")
    }

    operation("Получение отзывов", WktCommand.FEEDBACK_READ) {
      stubs("Обработка стабов") {
        stubReadFeedbackSuccess("Обработка успешного чтения")
        stubValidationFeedbackBadWorkoutId("Имитация ошибки плохого id тренировки")
      }
      validation {
        validateWorkoutId("Проверка на существование поля workout")
        finishValidation("Завершение проверок")
      }
      prepareResult("Подготовка ответа")
    }

    operation("Обновление отзыва", WktCommand.FEEDBACK_UPDATE) {
      stubs("Обработка стабов") {
        stubCreateFeedbackSuccess("Обработка успешного чтения")
        stubValidationFeedbackBadWorkoutId("Имитация ошибки плохого id тренировки")
        stubValidationFeedbackEmptyReview("Имитация ошибки плохого поля review")
      }
      validation {
        worker("Копируем поля в объект валидации") {
          feedbackValidity = feedbackUpdateRequest.copy()
        }
        worker("Очистка id") {
          feedbackValidity.workout = WktWorkoutId.NONE
        }
        worker("Очистка review") {
          feedbackValidity.review = "".trim()
        }
        validateWorkoutId("Проверка на существование workout")
        validateReviewIsExist("Проверка на существование review")
        finishValidation("Завершение проверок")
      }
      prepareResult("Подготовка ответа")
    }

    operation("Поиск тренировок", WktCommand.FEEDBACK_DELETE) {
      stubs("Обработка стабов") {
        stubFeedbackDeleteSuccess("Обработка успешного поиска")
        stubFeedbackDeleteFail("Имитация ошибки удаления отзыва")
      }
      validation {
        worker("Очищаем id") {
          feedbackValidity.id = WktFeedbackId.NONE
        }
        validateFeedbackIdIsExist("проверка на передачу id отзыва в запросе")
        finishValidation("Завершение проверок")
      }
      prepareResult("Подготовка ответа")
    }
  }.build()
}