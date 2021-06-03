package com.nemesis.rio.data.raiding.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.data.raiding.achievements.database.RaidAchievementsDao
import com.nemesis.rio.data.raiding.achievements.database.toRaidAchievementsList
import com.nemesis.rio.data.raiding.achievements.database.toRaidAchievementsMap
import com.nemesis.rio.data.raiding.progress.database.RaidProgressDao
import com.nemesis.rio.data.raiding.progress.database.RaidProgressEntity
import com.nemesis.rio.data.raiding.progress.database.toRaidProgress
import com.nemesis.rio.data.raiding.progress.database.toRaidProgressMap
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.source.CharacterRaidingSource

class CharacterRaidingDatabaseSource(
    private val raidProgressDao: RaidProgressDao,
    private val raidAchievementsDao: RaidAchievementsDao,
    private val profileIDProvider: ProfileIDProvider<Character>
) : CharacterRaidingSource {

    override suspend fun getProgressForRaid(character: Character, raid: Raid): RaidProgress =
        profileIDProvider.withProfileID(character) {
            raidProgressDao.getRaidProgressEntity(
                raid,
                it,
                RaidProgressEntity.PROFILE_TYPE_CHARACTER
            )?.toRaidProgress() ?: emptyMap()
        }

    override suspend fun getAllRaidProgress(character: Character): Map<Raid, RaidProgress> =
        profileIDProvider.withProfileID(character) {
            raidProgressDao.getAllRaidProgressEntities(
                it,
                RaidProgressEntity.PROFILE_TYPE_CHARACTER
            ).toRaidProgressMap()
        }

    override suspend fun getRaidAchievementsForRaid(
        character: Character,
        raid: Raid
    ): List<RaidAchievement> = profileIDProvider.withProfileID(character) {
        raidAchievementsDao.getRaidAchievementsEntity(raid, it)?.toRaidAchievementsList()
            ?: emptyList()
    }

    override suspend fun getAllRaidAchievements(character: Character): Map<Raid, List<RaidAchievement>> =
        profileIDProvider.withProfileID(character) {
            raidAchievementsDao.getAllRaidAchievementsEntities(it).toRaidAchievementsMap()
        }
}
