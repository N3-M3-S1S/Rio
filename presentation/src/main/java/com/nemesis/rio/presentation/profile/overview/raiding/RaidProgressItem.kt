package com.nemesis.rio.presentation.profile.overview.raiding

import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.progress.RaidProgress

data class RaidProgressItem(
    val raid: Raid,
    val raidProgress: RaidProgress = emptyMap(),
    val raidAchievements: List<RaidAchievement> = emptyList()
)
