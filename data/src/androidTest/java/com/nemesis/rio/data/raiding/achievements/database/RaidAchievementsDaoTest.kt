package com.nemesis.rio.data.raiding.achievements.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.AheadOfTheCurve
import com.nemesis.rio.domain.raiding.achievements.CuttingEdge
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals

class RaidAchievementsDaoTest : AppDatabaseTest() {
    private val raidAchievementsDao = appDatabase.raidAchievementsDao

    @Test
    fun saveAndGetAllAchievements() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val expectedDateTime = Clock.System.now()
        val expectedRaidAchievements = mapOf(
            Raid.ANTORUS_THE_BURNING_THRONE to listOf(
                AheadOfTheCurve(expectedDateTime),
                CuttingEdge(expectedDateTime)
            ),
            Raid.CRUCIBLE_OF_STORMS to listOf(AheadOfTheCurve(expectedDateTime))

        )

        raidAchievementsDao.saveOrUpdate(
            expectedRaidAchievements.toRaidAchievementsEntities(characterID)
        )

        val raidAchievements =
            raidAchievementsDao.getAllRaidAchievementsEntities(characterID)
                .toRaidAchievementsMap()
        assertEquals(expectedRaidAchievements, raidAchievements)
    }

    @Test
    fun updateAndGetAchievements() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val expectedDateTime = Clock.System.now()
        val expectedRaid = Raid.TOMB_OF_SARGERAS
        val expectedRaidAchievementsList =
            mutableListOf<RaidAchievement>(AheadOfTheCurve(expectedDateTime))

        val expectedRaidAchievements =
            mutableMapOf(expectedRaid to expectedRaidAchievementsList)
        raidAchievementsDao.saveOrUpdate(
            expectedRaidAchievements.toRaidAchievementsEntities(characterID)
        )

        expectedRaidAchievementsList.add(CuttingEdge(expectedDateTime))
        raidAchievementsDao.saveOrUpdate(
            expectedRaidAchievements.toRaidAchievementsEntities(characterID)
        )

        val raidAchievementsList =
            raidAchievementsDao.getRaidAchievementsEntity(expectedRaid, characterID)
                ?.toRaidAchievementsList()
        assertEquals(expectedRaidAchievementsList, raidAchievementsList)
    }


}
