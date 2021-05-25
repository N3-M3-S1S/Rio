package com.nemesis.rio.data.api.search

import com.nemesis.rio.data.api.search.serialization.GuildSearchResultDeserializer
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.ranks.RaidRanks
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable(with = GuildSearchResultDeserializer::class)
data class GuildSearchResponse(
    @Transient val guild: Guild,
    @Transient val raidRanks: Map<Raid, RaidRanks>,
    @Transient val raidProgress: Map<Raid, RaidProgress>,
)
