plugins {
    kotlin("js") version "1.5.31"
}

version = "0.1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")

    testImplementation(kotlin("test-js"))
}

kotlin {
    js(IR) {
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}
