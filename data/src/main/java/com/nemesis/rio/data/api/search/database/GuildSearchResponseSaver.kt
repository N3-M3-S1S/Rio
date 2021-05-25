package com.nemesis.rio.data.api.search.database

import com.nemesis.rio.data.api.search.GuildSearchResponse
import com.nemesis.rio.data.profile.database.ProfileSaver
import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.ranks.RaidRanks

class GuildSearchResponseSaver(
    private val profileSaver: ProfileSaver<Guild>,
    private val raidRanksSaver: ProfileProgressSaver<Map<Raid, RaidRanks>>,
    private val raidProgressSaver: ProfileProgressSaver<Map<Raid, RaidProgress>>
) {

    suspend fun saveOrUpdateResponseContent(response: GuildSearchResponse) {
        with(response) {
            val guildID = profileSaver.saveOrUpdateProfileAndCacheID(guild)
            raidRanksSaver.saveOrUpdate(raidRanks, guildID)
            raidProgressSaver.saveOrUpdate(raidProgress, guildID)
        }
    }
}
