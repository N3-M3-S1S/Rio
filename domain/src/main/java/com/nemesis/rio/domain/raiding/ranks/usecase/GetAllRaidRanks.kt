package com.nemesis.rio.domain.raiding.ranks.usecase

import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.ranks.RaidRanks
import com.nemesis.rio.domain.raiding.source.GuildRaidingSource

class GetAllRaidRanks(private val guildRaidingSource: GuildRaidingSource) {

    suspend operator fun invoke(guild: Guild): Map<Raid, RaidRanks> =
        guildRaidingSource.getAllRaidRanks(guild)
}
