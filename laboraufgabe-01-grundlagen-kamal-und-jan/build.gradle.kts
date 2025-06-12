import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.*


plugins {
    kotlin("jvm") version "1.5.30"
    application
}

group = "de.fh-flensburg.webtech"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.7.0")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")
}

tasks {
    test {
        useJUnitPlatform()

        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = mutableSetOf(FAILED, PASSED, SKIPPED)
        }
    }


}

tasks.register("MusterTaskName") {
    dependsOn("build")
    println("DOne")

}

application { mainClass.set("MainKt") }