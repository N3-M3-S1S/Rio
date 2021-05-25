package com.nemesis.rio.data.raiding.ranks.database

import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.ranks.RaidRanks

class GuildRaidRanksSaver(private val raidRanksDao: RaidRanksDao) :
    ProfileProgressSaver<Map<Raid, RaidRanks>> {

    override suspend fun saveOrUpdate(progress: Map<Raid, RaidRanks>, profileId: Long) {
        raidRanksDao.deleteRanksIfRaidIsNotInList(progress.keys.toList(), profileId)
        raidRanksDao.saveOrUpdate(progress.toRaidRanksEntities(profileId))
    }
}
