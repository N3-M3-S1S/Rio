package com.nemesis.rio.presentation.profile.overview.character.overall

import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement

data class CharacterOverallRaidingData(
    val raid: Raid,
    val difficulty: Difficulty,
    val progress: Int,
    val achievement: RaidAchievement?
)
