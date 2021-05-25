plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version ProjectDependencies.KOTLIN_VERSION
}

allprojects {
    repositories {
        google()
        jcenter()
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
        classpath(AppDependencies.NAVIGATION_SAFE_ARGS_PLUGIN)
    }
}
