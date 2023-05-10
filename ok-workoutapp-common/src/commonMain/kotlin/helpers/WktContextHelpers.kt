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