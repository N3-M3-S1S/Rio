package com.nemesis.rio.presentation.raiding

import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.presentation.R

val Difficulty.stringResId: Int
    get() = when (this) {
        Difficulty.NORMAL -> R.string.difficulty_normal
        Difficulty.HEROIC -> R.string.difficulty_heroic
        Difficulty.MYTHIC -> R.string.difficulty_mythic
    }
