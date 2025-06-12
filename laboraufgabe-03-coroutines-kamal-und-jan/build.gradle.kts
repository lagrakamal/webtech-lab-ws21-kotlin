plugins {
    kotlin("jvm") version "1.4.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.7.0")
    testImplementation("org.jetbrains.kotlin","kotlin-reflect","1.4.10")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")

    implementation("io.projectreactor","reactor-core","3.4.0")
    testImplementation("io.projectreactor","reactor-test","3.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.4.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            events = mutableSetOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
            )
        }
    }
}

application { mainClass.set("MainKt") }