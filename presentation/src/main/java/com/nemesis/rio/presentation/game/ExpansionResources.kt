package com.nemesis.rio.presentation.game

import androidx.annotation.StringRes
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.presentation.R

val Expansion.stringResId
    @StringRes
    get() = when (this) {
        Expansion.BFA -> R.string.expansion_bfa
        Expansion.LEGION -> R.string.expansion_legion
        Expansion.SHADOWLANDS -> R.string.expansion_shadowlands
    }
