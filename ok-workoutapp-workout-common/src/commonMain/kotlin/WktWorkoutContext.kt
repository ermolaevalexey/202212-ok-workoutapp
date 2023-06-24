package ru.otus.otuskotlin.workoutapp.workout.common

import WktCorSettings
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.workoutapp.common.constants.*
import ru.otus.otuskotlin.workoutapp.common.models.*
import ru.otus.otuskotlin.workoutapp.workout.common.models.*
import ru.otus.otuskotlin.workoutapp.common.stubs.WktStub
import ru.otus.otuskotlin.workoutapp.workout.common.repo.IWorkoutRepository

data class WktWorkoutContext(
  override var command: WktCommand = WktCommand.NONE,
  override var state: WktState = WktState.NONE,
  override var errors: MutableList<WktError> = mutableListOf(),

  var workMode: WktWorkMode = WktWorkMode.PROD,
  override var stubCase: WktStub = WktStub.NONE,

  var requestId: WktRequestId = WktRequestId.NONE,
  var timeStart: Instant = Instant.NONE,
  var settings: WktCorSettings = WktCorSettings.NONE,
  var workoutRepo: IWorkoutRepository = IWorkoutRepository.NONE,
  var workoutValidity: WktWorkout = WktWorkout(),
  var workoutValid: WktWorkout = WktWorkout(),

  var workoutCreateRequest: WktWorkout = WktWorkout(),
  var workoutReadRequest: WktWorkoutId = WktWorkoutId.NONE,
  var workoutUpdateRequest: WktWorkout = WktWorkout(),
  var workoutSearchRequest: WktWorkoutSearch = WktWorkoutSearch(),

  var workoutCreateResponse: WktWorkout = WktWorkout(),
  var workoutUpdateResponse: WktWorkout = WktWorkout(),
  var workoutReadResponse: WktWorkout = WktWorkout(),
  var workoutSearchResponse: WktWorkoutSearchPayload = WktWorkoutSearchPayload(),

  var workoutResponse: Any = WktWorkout(),
  ) : WktContext