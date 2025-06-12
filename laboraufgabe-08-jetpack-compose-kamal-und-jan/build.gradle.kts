version = "1.0"

repositories {
    mavenCentral()
}
buildscript {
    val kotlin_version by extra("1.5.21")
    val compose_version by extra("1.0.1")

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }

    repositories {
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("com.android.tools.lint:lint:27.2.2")
    }
}
