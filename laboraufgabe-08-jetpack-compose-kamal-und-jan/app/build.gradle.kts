plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.5.21"
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdkVersion(31)

    defaultConfig {
        applicationId = "de.hsflensburg.webtech.lab08"
        minSdkVersion(21)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
        kotlinCompilerVersion = "1.5.21"
    }

    dexOptions {
        javaMaxHeapSize = "3g"
    }

    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("androidx.appcompat:appcompat:1.4.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("androidx.compose.ui:ui:1.0.5")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.0.5")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.0.5")
    // Material Design
    implementation("androidx.compose.material:material:1.0.5")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.0.5")
    implementation("androidx.compose.material:material-icons-extended:1.0.5")

    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("io.ktor:ktor-client-core:1.6.4")
    implementation("io.ktor:ktor-client-android:1.6.4")
    implementation("io.ktor:ktor-client-serialization:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}
