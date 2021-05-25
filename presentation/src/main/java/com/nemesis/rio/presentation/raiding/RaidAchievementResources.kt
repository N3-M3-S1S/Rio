package com.nemesis.rio.presentation.raiding

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.raiding.achievements.AheadOfTheCurve
import com.nemesis.rio.domain.raiding.achievements.CuttingEdge
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.presentation.R

val RaidAchievement.shortStringResId: Int
    @StringRes
    get() = when (this) {
        is AheadOfTheCurve -> R.string.raid_achievement_aotc_short
        is CuttingEdge -> R.string.raid_achievement_ce_short
    }

val RaidAchievement.colorResId: Int
    @ColorRes
    get() = when (this) {
        is AheadOfTheCurve -> R.color.quality_epic
        is CuttingEdge -> R.color.quality_legendary
    }
