plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm {}
  macosX64 {}
  linuxX64 {}

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib-common"))

        implementation(project(":ok-workoutapp-common"))
        implementation(project(":ok-workoutapp-workout-common"))
        implementation(project(":ok-workoutapp-feedback-common"))
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation(kotlin("stdlib"))
        implementation(project(":ok-workoutapp-common"))
        implementation(project(":ok-workoutapp-workout-common"))
        implementation(project(":ok-workoutapp-feedback-common"))
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
      }
    }
  }
}