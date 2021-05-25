package com.nemesis.rio.presentation.profile.character.attributes

import androidx.annotation.DrawableRes
import com.nemesis.rio.domain.profile.character.attributes.Covenant
import com.nemesis.rio.presentation.R

val Covenant.imageResID: Int
    @DrawableRes
    get() = when (this) {
        Covenant.KYRIAN -> R.drawable.covenant_kyrian
        Covenant.NECROLORD -> R.drawable.covenant_necrolord
        Covenant.NIGHT_FAE -> R.drawable.covenant_night_fae
        Covenant.VENTHYR -> R.drawable.covenant_venthyr
    }
