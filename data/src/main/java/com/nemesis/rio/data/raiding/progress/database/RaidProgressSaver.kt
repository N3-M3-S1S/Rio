package com.nemesis.rio.data.raiding.progress.database

import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress

class RaidProgressSaver(
    private val raidProgressDao: RaidProgressDao,
    private val profileType: Int
) :
    ProfileProgressSaver<Map<Raid, RaidProgress>> {

    override suspend fun saveOrUpdate(progress: Map<Raid, RaidProgress>, profileId: Long) {
        raidProgressDao.deleteProgressIfRaidIsNotInList(
            progress.keys.toList(),
            profileId,
            profileType
        )
        raidProgressDao.saveOrUpdate(progress.toRaidProgressEntities(profileId, profileType))
    }
}
