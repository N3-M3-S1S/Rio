package com.nemesis.rio.presentation.app.initialization

import com.nemesis.rio.presentation.BuildConfig

class ApplicationInitializationState {

    private val currentApplicationVersionCode = BuildConfig.VERSION_CODE

    // the application needs initialization if it is launched first time or if it has been updated
    fun applicationNeedsInitialization(): Boolean =
        ApplicationVersionCodeStorage.versionCode != currentApplicationVersionCode

    fun setApplicationInitialized() {
        ApplicationVersionCodeStorage.versionCode = currentApplicationVersionCode
    }
}
