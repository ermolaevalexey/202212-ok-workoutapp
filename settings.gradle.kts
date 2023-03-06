rootProject.name = "ok-workoutapp-202212"

pluginManagement {
  val kotlinVersion: String by settings
  val kotestVersion: String by settings
  val openapiVersion: String by settings

  plugins {
    kotlin("jvm") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion apply false
    id("io.kotest.multiplatform") version kotestVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false

    id("org.openapi.generator") version openapiVersion apply false
  }
}

include("m1l1-quickstart")
include("m1l4-dsl")
include("m1l5-coroutines")
include("m2l3-testing")
include("ok-marketplace-api-v1-jackson")
include("ok-workoutapp-api-v1-jackson")
include("ok-workoutapp-common")
include("ok-workoutapp-mappers-v1")