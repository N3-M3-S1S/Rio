package com.nemesis.rio.presentation.profile.overview.guild.overall

import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.ranks.Ranks

data class GuildOverallRaidingData(
    val raid: Raid,
    val difficulty: Difficulty,
    val bossesKilled: Int,
    val ranks: Ranks?
)
