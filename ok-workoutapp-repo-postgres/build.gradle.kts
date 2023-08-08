plugins {
  kotlin("jvm")
}

dependencies {
  val exposedVersion: String by project
  val postgresDriverVersion: String by project
  val kmpUUIDVersion: String by project
  val testContainersVersion: String by project

  implementation(kotlin("stdlib"))

  implementation(project(":ok-workoutapp-common"))
  implementation(project(":ok-workoutapp-workout-common"))
  implementation(project(":ok-workoutapp-feedback-common"))

  implementation("org.postgresql:postgresql:$postgresDriverVersion")

  implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
  implementation("com.benasher44:uuid:$kmpUUIDVersion")


  testImplementation("org.testcontainers:postgresql:$testContainersVersion")
//  testImplementation(project(":ok-marketplace-repo-tests"))
}