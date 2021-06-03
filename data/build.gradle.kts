plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    `kotlin-common-configuration-and-dependencies`
    `android-common-configuration-and-dependencies`
}

android {
    //add shared test code and resources to both instrumented and kotlin tests
    sourceSets {
        val sharedTestCodeDirectory = "src/sharedTest/java"
        val sharedTestResourcesDirectory = "src/sharedTest/resources"
        val sourceSetsNames = arrayOf("test", "androidTest")
        sourceSetsNames.forEach { sourceSetName ->
            val sourceSet = getByName(sourceSetName)
            with(sourceSet) {
                java.srcDirs(sharedTestCodeDirectory)
                resources.srcDirs(sharedTestResourcesDirectory)
            }
        }
    }

    defaultConfig {
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", true)
                arg("room.expandProjection", true)
            }
        }
    }

    buildTypes.getByName("release").consumerProguardFiles("consumer-rules.pro")

}

dependencies {
    with(DataDependencies) {
        api(ROOM)
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)

        implementation(KOTLINX_SERIALIZATION)

        implementation(RETROFIT)
        implementation(RETROFIT_KOTLINX_SERIALIZATION_CONVERTER)

        testImplementation(MOCK_WEB_SERVER)
    }
    implementation(project(":domain"))
    implementation(project(":utils"))
}
