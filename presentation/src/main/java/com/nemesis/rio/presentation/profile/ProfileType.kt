package com.nemesis.rio.presentation.profile

import com.nemesis.rio.presentation.R

enum class ProfileType {
    CHARACTER, GUILD
}

val ProfileType.stringResId
    get() = when (this) {
        ProfileType.CHARACTER -> R.string.profile_character
        ProfileType.GUILD -> R.string.profile_guild
    }
