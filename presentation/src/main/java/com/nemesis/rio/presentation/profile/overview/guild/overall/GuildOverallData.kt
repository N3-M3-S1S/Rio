package com.nemesis.rio.presentation.profile.overview.guild.overall

import com.nemesis.rio.domain.profile.Guild
import kotlinx.datetime.Instant

data class GuildOverallData(
    val guild: Guild,
    val raidingData: GuildOverallRaidingData,
    val lastUpdate: Instant
)
