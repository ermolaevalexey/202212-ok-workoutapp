rootProject.name = "ok-workoutapp-202212"

pluginManagement {
  val kotlinVersion: String by settings
  val kotestVersion: String by settings
  val openapiVersion: String by settings
  val ktorVersion: String by settings
  val bmuschkoVersion: String by settings
  val serializationVersion: String by settings

  plugins {
    kotlin("jvm") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion apply false
    id("io.kotest.multiplatform") version kotestVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    id("kotlinx-serialization") version serializationVersion apply false
    id("io.ktor.plugin") version ktorVersion apply false
    id("org.openapi.generator") version openapiVersion apply false
    id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
    id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
  }
}

include("m1l1-quickstart")
include("m1l4-dsl")
include("m1l5-coroutines")
include("m2l3-testing")
include("ok-workoutapp-api-v1-jackson")
include("ok-workoutapp-common")
include("ok-workoutapp-feedback-common")
include("ok-workoutapp-workout-common")
include("ok-workoutapp-mappers-v1")
include("ok-workoutapp-workout-app-ktor")
include("ok-workoutapp-stubs")
include("ok-workoutapp-biz")
include("ok-workoutapp-app-kafka")
include("ok-workoutapp-lib-cor")
include("ok-workoutapp-repo-in-memory")
//include("ok-workoutapp-repo-tests")
//include("ok-workoutapp-repo-stubs")