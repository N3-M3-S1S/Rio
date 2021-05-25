package com.nemesis.rio.domain.raiding.source

import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.ranks.RaidRanks

interface GuildRaidingSource {
    suspend fun getProgressForRaid(guild: Guild, raid: Raid): RaidProgress
    suspend fun getAllRaidProgress(guild: Guild): Map<Raid, RaidProgress>
    suspend fun getRanksForRaid(guild: Guild, raid: Raid): RaidRanks
    suspend fun getAllRaidRanks(guild: Guild): Map<Raid, RaidRanks>
}
