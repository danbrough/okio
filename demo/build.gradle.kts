import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
  kotlin("multiplatform")
}


kotlin {
  jvm()

  linuxX64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.danbrough.klog:klog:0.0.1-beta04")
      }

    }
  }

  targets.withType(KotlinNativeTarget::class).all {
    println("NATIVE TARGET: $this")
    compilations["main"].apply {

    }

    binaries {
      executable("demo") {
        entryPoint("demo.Main")
      }
    }
  }

  targets.withType(KotlinJvmTarget::class).all {
    println("JVM TARGET: $this")
    compilations["main"].apply {

    }
  }
}


tasks.withType<AbstractTestTask>() {
  testLogging {
    events = setOf(
      TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED
    )
    exceptionFormat = TestExceptionFormat.FULL
    showStandardStreams = true
    showStackTraces = true
  }
  outputs.upToDateWhen {
    false
  }
}
