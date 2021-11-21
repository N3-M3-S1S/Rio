plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0-RC2")
}
