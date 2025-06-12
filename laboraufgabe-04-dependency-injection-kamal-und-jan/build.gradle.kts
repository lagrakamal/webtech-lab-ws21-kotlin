import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.7.0")
    testImplementation("org.jetbrains.kotlin","kotlin-reflect","1.4.10")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")

    implementation("org.kodein.di:kodein-di:7.8.0")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
