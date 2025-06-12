import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "de.hsflensburg"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            appResourcesRootDir.set(project.layout.projectDirectory.dir("xxx"))
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinJvmComposeDesktopApplication"
            packageVersion = "1.0.0"
        }
    }
}
