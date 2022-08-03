import com.android.builder.core.DefaultApiVersion

android {
    compileSdkVersion(32)
    defaultConfig {
        minSdkVersion = DefaultApiVersion(23)
        targetSdkVersion = DefaultApiVersion(32)
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lintOptions {
        isAbortOnError = false
    }
    kotlinOptions.jvmTarget = "11"
}

dependencies {
    with(AndroidDependencies) {
        implementation(ANDROID_CORE_KTX)
        implementation(ANDROID_COLLECTIONS)
        androidTestImplementation(ANDROID_TEST_RUNNER)
        androidTestImplementation(ANDROID_TEST_RULES)
        androidTestImplementation(ANDROID_MOCKK)
        implementation(TIMBER)
        add("coreLibraryDesugaring", CORE_LIBRARY_DESUGARING)
    }
    androidTestImplementation(ProjectDependencies.KOTLIN_TEST)
}
