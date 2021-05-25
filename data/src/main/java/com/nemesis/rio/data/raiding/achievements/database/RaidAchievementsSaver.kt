package com.nemesis.rio.data.raiding.achievements.database

import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement

class RaidAchievementsSaver(private val raidAchievementsDao: RaidAchievementsDao) :
    ProfileProgressSaver<Map<Raid, List<RaidAchievement>>> {

    override suspend fun saveOrUpdate(progress: Map<Raid, List<RaidAchievement>>, profileId: Long) {
        raidAchievementsDao.saveOrUpdate(progress.toRaidAchievementsEntities(profileId))
    }
}
