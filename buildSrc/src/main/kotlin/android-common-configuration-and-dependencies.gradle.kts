android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    with(AndroidDependencies) {
        implementation(ANDROID_CORE_KTX)
        implementation(ANDROID_COLLECTIONS_KTX)
        androidTestImplementation(ANDROID_TEST_RUNNER)
        androidTestImplementation(ANDROID_TEST_RULES)
        androidTestImplementation(ANDROID_MOCKK)
        implementation(TIMBER)
        add("coreLibraryDesugaring", CORE_LIBRARY_DESUGARING)
    }
    androidTestImplementation(ProjectDependencies.KOTLIN_TEST)
}
