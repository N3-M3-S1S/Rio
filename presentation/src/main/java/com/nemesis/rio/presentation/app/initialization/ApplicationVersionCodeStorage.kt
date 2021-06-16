package com.nemesis.rio.presentation.app.initialization

import com.chibatching.kotpref.KotprefModel

object ApplicationVersionCodeStorage : KotprefModel() {
    var versionCode by intPref()
}
