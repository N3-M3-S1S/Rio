plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version ProjectDependencies.KOTLIN_VERSION
    id("com.github.ben-manes.versions") version ProjectDependencies.DEPENDENCY_UPDATES_CHECKER_PLUGIN_VERSION
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ProjectDependencies.KOTLIN_GRADLE_PLUGIN)
        classpath(ProjectDependencies.ANDROID_GRADLE_PLUGIN)
        classpath(PresentationDependencies.NAVIGATION_SAFE_ARGS_PLUGIN)
    }
}
