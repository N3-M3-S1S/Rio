package com.nemesis.rio.presentation.profile.character.attributes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.presentation.R

val Role.nameResId: Int
    @StringRes
    get() = when (this) {
        Role.DAMAGE_DEALER -> R.string.role_damage_dealer
        Role.HEALER -> R.string.role_healer
        Role.TANK -> R.string.role_tank
    }

val Role.iconResId: Int
    @DrawableRes
    get() = when (this) {
        Role.DAMAGE_DEALER -> R.drawable.role_damage_dealer
        Role.HEALER -> R.drawable.role_healer
        Role.TANK -> R.drawable.role_tank
    }
