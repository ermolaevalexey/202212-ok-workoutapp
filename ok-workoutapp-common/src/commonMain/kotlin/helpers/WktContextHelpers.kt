package ru.otus.otuskotlin.workoutapp.common.helpers

import ru.otus.otuskotlin.workoutapp.common.models.WktError

fun errorValidation(
  field: String,
  violationCode: String,
  description: String,
) = WktError(
  code = "validation-$field-$violationCode",
  field = field,
  group = "validation",
  message = "Validation error for field $field: $description"
)

fun errorAdministration(
  /**
   * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
   * Например: empty, badSymbols, tooLong, etc
   */
  field: String = "",
  violationCode: String,
  description: String,
  exception: Exception? = null,
  level: String = "",
) = WktError(
  field = field,
  code = "administration-$violationCode",
  group = "administration",
  message = "Microservice management error: $description",
  level = level,
  exception = exception,
)

val errorNotFound = WktError(
  field = "id",
  message = "Not Found",
  code = "not-found"
)

val errorEmptyId = WktError(
  field = "id",
  message = "Id must not be null or blank"
)