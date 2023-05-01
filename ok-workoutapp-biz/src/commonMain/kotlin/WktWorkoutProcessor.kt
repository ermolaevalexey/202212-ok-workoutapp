package ru.otus.otuskotlin.workoutapp.biz

import ru.otus.otuskotlin.workoutapp.stubs.WktWorkoutOwnWeightStub
import ru.otus.otuskotlin.workoutapp.workout.common.WktWorkoutContext

class WktWorkoutProcessor {

  suspend fun exec(ctx: WktWorkoutContext) {
    ctx.workoutReadResponse = WktWorkoutOwnWeightStub.WKT_WORKOUT
  }
}