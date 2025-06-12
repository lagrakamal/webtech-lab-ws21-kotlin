import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

application {
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
    mainClassName = "ApplicationKt"
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.7.0")
    testImplementation("org.jetbrains.kotlin","kotlin-reflect","1.4.10")
    testImplementation("org.jsoup:jsoup:1.13.1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.7.0")

    implementation("org.kodein.di:kodein-di:7.8.0")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:7.8.0")

    implementation("io.ktor:ktor-server-core:1.6.4")
    implementation("io.ktor:ktor-server-netty:1.6.4")
    implementation("io.ktor:ktor-html-builder:1.6.4")
    implementation("io.ktor:ktor-serialization:1.6.4")
    implementation("io.ktor:ktor-server-test-host:1.6.4")

    implementation("ch.qos.logback:logback-classic:1.2.5")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
