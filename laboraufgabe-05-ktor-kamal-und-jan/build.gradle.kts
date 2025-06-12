import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
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
    testImplementation("org.jsoup:jsoup:1.13.1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")

    implementation("org.kodein.di:kodein-di:7.1.0")

    implementation("io.ktor:ktor-server-core:1.4.2")
    implementation("io.ktor:ktor-server-netty:1.4.2")
    implementation("io.ktor:ktor-server-test-host:1.4.2")
    implementation("io.ktor:ktor-html-builder:1.4.2")
    implementation("ch.qos.logback:logback-classic:0.9.26")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
