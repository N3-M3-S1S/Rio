plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    `kotlin-common-configuration-and-dependencies`
    `android-common-configuration-and-dependencies`
}

android {
    defaultConfig {
        applicationId("com.nemesis.rio")
        versionCode(2)
        versionName("1.1")
        base {
            archivesBaseName = "${rootProject.name}-$versionName"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes.getByName("release") {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    buildTypes.map { it.javaCompileOptions.annotationProcessorOptions.arguments }
        .forEach { arguments ->
            arguments["logEpoxyTimings"] = "true"
            arguments["enableParallelEpoxyProcessing"] = "true"
        }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    with(PresentationDependencies) {
        implementation(KOIN_ANDROID)
        implementation(KOIN_EXT)
        implementation(KOIN_WORK_MANAGER)

        implementation(KOTPREF)
        implementation(KOTPREF_INITIALIZER)
        implementation(KOTPREF_LIVEDATA)
        implementation(KOTPREF_ENUM)

        implementation(EPOXY)
        kapt(EPOXY_PROCESSOR)
        implementation(EPOXY_DATABINDING)

        implementation(VIEWMODEL)
        implementation(LIVEDATA)
        implementation(LIFECYCLE_COMPILER)

        implementation(NAVIGATION_UI)
        implementation(NAVIGATION_FRAGMENT)

        implementation(ACTIVITY)
        implementation(FRAGMENT)
        implementation(APP_COMPAT)
        implementation(BROWSER)

        implementation(MATERIAL_COMPONENTS)
        implementation(CONSTRAINT_LAYOUT)
        implementation(FLEX_BOX_LAYOUT)
        implementation(SWIPE_REFRESH_LAYOUT)
        implementation(ASYNC_LAYOUT_INFLATER)

        implementation(COIL)
        implementation(SPLITTIES)
        implementation(VIEW_BINDING_PROPERTY_DELEGATE)
        debugImplementation(LEAK_CANARY)
    }
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":utils"))
}
