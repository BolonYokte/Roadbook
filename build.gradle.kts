// File: build.gradle.kts (en la carpeta raíz)

plugins {
    // NO cambies aquí las versiones, sólo mantenlas apply false
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    kotlin("jvm")
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}
