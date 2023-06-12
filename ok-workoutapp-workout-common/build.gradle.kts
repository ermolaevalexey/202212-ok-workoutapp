plugins {
  kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version


kotlin {
  jvm {}
  macosX64 {}
  linuxX64 {}

  sourceSets {
    val datetimeVersion: String by project

    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib-common"))
        implementation(project(":ok-workoutapp-common"))
        implementation(project(":ok-workoutapp-repo-in-memory"))
        api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(project(":ok-workoutapp-repo-in-memory"))
        implementation(kotlin("test-annotations-common"))
      }
    }
  }
}