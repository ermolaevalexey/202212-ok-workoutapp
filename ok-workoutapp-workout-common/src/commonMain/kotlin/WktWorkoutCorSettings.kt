package ru.otus.otuskotlin.workoutapp.workout.common

import ru.otus.otuskotlin.workoutapp.workout.common.repo.IWorkoutRepository

data class WktWorkoutCorSettings(
  val workoutRepoStub: IWorkoutRepository = IWorkoutRepository.NONE,
  val workoutRepoTest: IWorkoutRepository = IWorkoutRepository.NONE,
  val workoutRepoProd: IWorkoutRepository = IWorkoutRepository.NONE,
) {
  companion object {
    val NONE = WktWorkoutCorSettings()
  }
}
