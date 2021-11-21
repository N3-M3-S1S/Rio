import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    autoCorrect = true
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "11"
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += listOf(
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xjvm-default=all"
    )
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.name.equals("kotlin-reflect")) {
            useVersion(ProjectDependencies.KOTLIN_VERSION)
        }
    }
}

dependencies {
    with(ProjectDependencies) {
        implementation(COROUTINES)
        implementation(KOTLIN_DATE_TIME)
        testImplementation(COROUTINES_TEST)
        testImplementation(MOCKK)
        testImplementation(KOTLIN_TEST)
        detektPlugins(DETEKT_FORMATTING)
    }
}
