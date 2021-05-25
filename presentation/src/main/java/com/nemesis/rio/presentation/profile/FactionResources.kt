package com.nemesis.rio.presentation.profile

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.presentation.R

val Faction.stringResId: Int
    @StringRes
    get() = when (this) {
        Faction.ALLIANCE -> R.string.faction_alliance
        Faction.HORDE -> R.string.faction_horde
    }

val Faction.colorResId: Int
    @ColorRes
    get() = when (this) {
        Faction.ALLIANCE -> R.color.faction_alliance
        Faction.HORDE -> R.color.faction_horde
    }
