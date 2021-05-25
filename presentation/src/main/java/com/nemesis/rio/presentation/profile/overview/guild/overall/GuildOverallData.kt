package com.nemesis.rio.presentation.profile.overview.guild.overall

import com.nemesis.rio.domain.profile.guild.Guild
import kotlinx.datetime.LocalDateTime

data class GuildOverallData(
    val guild: Guild,
    val raidingData: GuildOverallRaidingData,
    val lastUpdateDateTime: LocalDateTime
)
