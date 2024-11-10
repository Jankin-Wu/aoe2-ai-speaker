import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization") version "2.0.20"
}

group = "com.jankinwu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(files("libs/fluent-icons-core-desktop-0.0.1-dev.8.jar"))
    implementation(files("libs/fluent-desktop-0.0.1-dev.8.jar"))
    implementation(files("libs/fluent-icons-extended-desktop-0.0.1-dev.8.jar"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "aoe2-ai-speaker"
            packageVersion = "1.0.0"
        }
    }
}
