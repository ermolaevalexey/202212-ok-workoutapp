import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
  "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
  kotlin("jvm")
  id("io.ktor.plugin")
}

application {
  mainClass.set("ok.workoutapp.workout.app.ktor.ApplicationKt")

//    val isDevelopment: Boolean = project.ext.has("development")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-caching-headers-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-auto-head-response-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-websockets-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")

  //transport models
  implementation(project(":ok-workoutapp-api-v1-jackson"))
  implementation(project(":ok-workoutapp-mappers-v1"))
  implementation(project(":ok-workoutapp-common"))
  implementation(project(":ok-workoutapp-workout-common"))
  implementation(project(":ok-workoutapp-feedback-common"))
  implementation(project(":ok-workoutapp-biz"))
  implementation(project(":ok-workoutapp-repo-in-memory"))

  //stubs
  implementation(project(":ok-workoutapp-stubs"))

  implementation(ktor("jackson", "serialization"))
  implementation(kotlin("test-junit"))
  implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
  implementation(ktor("content-negotiation", prefix = "client-"))
  implementation("io.ktor:ktor-server-websockets")
  implementation(project(mapOf("path" to ":ok-workoutapp-biz")))
  // implementation(ktor("websockets")) // "io.ktor:ktor-server-websockets:$ktorVersion"

  testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
  testImplementation(ktor("content-negotiation", prefix = "client-"))
  testImplementation("io.ktor:ktor-client-websockets")
  // testImplementation(ktor("websockets", prefix = "client-"))
}