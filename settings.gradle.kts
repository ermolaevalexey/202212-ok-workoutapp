rootProject.name = "ok-workoutapp-202212"

pluginManagement {
  val kotlinVersion: String by settings
  val kotestVersion: String by settings

  plugins {
    kotlin("jvm") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion
    id("io.kotest.multiplatform") version kotestVersion apply false
  }
}

include("m1l1-quickstart")
include("m1l4-dsl")
include("m1l5-coroutines")
include("m2l3-testing")
