package com.nemesis.rio.data.raiding.progress.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.data.profile.database.createTestGuildInDatabase
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RaidProgressDaoTest : AppDatabaseTest() {
    private val raidProgressDao = appDatabase.raidProgressDao

    @Test
    fun saveAndGetAllRaidProgress() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val guildID = createTestGuildInDatabase()

        val expectedCharacterRaidProgress = mapOf(
            Raid.TRIAL_OF_VALOR to mapOf(
                Difficulty.NORMAL to 3,
                Difficulty.HEROIC to 2,
                Difficulty.MYTHIC to 1
            ),
            Raid.ANTORUS_THE_BURNING_THRONE to mapOf(
                Difficulty.NORMAL to 10,
                Difficulty.HEROIC to 0,
                Difficulty.MYTHIC to 0
            )
        )

        val expectedGuildRaidProgress = mapOf(
            Raid.BATTLE_OF_DAZARALOR to mapOf(
                Difficulty.NORMAL to 10,
                Difficulty.HEROIC to 10,
                Difficulty.MYTHIC to 10
            )
        )

        raidProgressDao.saveOrUpdate(
            expectedCharacterRaidProgress.toRaidProgressEntities(
                characterID,
                RaidProgressEntity.PROFILE_TYPE_CHARACTER
            )
        )
        raidProgressDao.saveOrUpdate(
            expectedGuildRaidProgress.toRaidProgressEntities(
                guildID,
                RaidProgressEntity.PROFILE_TYPE_GUILD
            )
        )

        val characterRaidProgress = raidProgressDao.getAllRaidProgressEntities(
            characterID,
            RaidProgressEntity.PROFILE_TYPE_CHARACTER
        ).toRaidProgressMap()

        assertEquals(expectedCharacterRaidProgress, characterRaidProgress)

        val guildRaidProgress = raidProgressDao.getAllRaidProgressEntities(
            guildID,
            RaidProgressEntity.PROFILE_TYPE_GUILD
        ).toRaidProgressMap()

        assertEquals(expectedGuildRaidProgress, guildRaidProgress)

    }

    @Test
    fun updateAndGetRaidProgress() = runBlocking {
        val guildID = createTestGuildInDatabase()
        val raid = Raid.BATTLE_OF_DAZARALOR
        val expectedRaidProgress = mutableMapOf(Difficulty.NORMAL to 1, Difficulty.HEROIC to 2)

        raidProgressDao.saveOrUpdate(
            mapOf(raid to expectedRaidProgress).toRaidProgressEntities(
                guildID,
                RaidProgressEntity.PROFILE_TYPE_GUILD
            )
        )
        expectedRaidProgress[Difficulty.MYTHIC] = 3
        raidProgressDao.saveOrUpdate(
            mapOf(raid to expectedRaidProgress).toRaidProgressEntities(
                guildID,
                RaidProgressEntity.PROFILE_TYPE_GUILD
            )
        )

        val result =
            raidProgressDao.getRaidProgressEntity(
                raid,
                guildID,
                RaidProgressEntity.PROFILE_TYPE_GUILD
            )?.toRaidProgress()

        assertEquals(expectedRaidProgress, result)
    }

    @Test
    fun deleteIfRaidIsNotInList() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val profileType = RaidProgressEntity.PROFILE_TYPE_CHARACTER
        val expectedRaids =
            setOf(Raid.ANTORUS_THE_BURNING_THRONE, Raid.ULDIR, Raid.THE_ETERNAL_PALACE)
        val expectedRaidProgress = enumValues<Raid>().associateWith {
            mapOf(
                Difficulty.NORMAL to 3,
                Difficulty.HEROIC to 2,
                Difficulty.MYTHIC to 1
            )
        }

        raidProgressDao.saveOrUpdate(
            expectedRaidProgress.toRaidProgressEntities(
                characterID,
                profileType
            )
        )
        val raidProgress =
            raidProgressDao.getAllRaidProgressEntities(characterID, profileType).toRaidProgressMap()
        assertEquals(expectedRaidProgress, raidProgress)

        raidProgressDao.deleteProgressIfRaidIsNotInList(
            expectedRaids.toList(),
            characterID,
            profileType
        )

        val raidProgressAfterDelete =
            raidProgressDao.getAllRaidProgressEntities(characterID, profileType).toRaidProgressMap()
        assertEquals(expectedRaids, raidProgressAfterDelete.keys)
    }

    @Test
    fun deleteAllProgress() = runBlocking {
        val guildID = createTestGuildInDatabase()
        val profileType = RaidProgressEntity.PROFILE_TYPE_GUILD
        val expectedRaidProgress = enumValues<Raid>().associateWith {
            mapOf(
                Difficulty.NORMAL to 3,
                Difficulty.HEROIC to 2,
                Difficulty.MYTHIC to 1
            )
        }

        raidProgressDao.saveOrUpdate(
            expectedRaidProgress.toRaidProgressEntities(
                guildID,
                profileType
            )
        )

        val raidProgress =
            raidProgressDao.getAllRaidProgressEntities(guildID, profileType).toRaidProgressMap()
        assertEquals(expectedRaidProgress, raidProgress)

        raidProgressDao.deleteAllProgress(guildID, profileType)

        val raidProgressAfterDelete =
            raidProgressDao.getAllRaidProgressEntities(guildID, profileType).toRaidProgressMap()
        assertTrue(raidProgressAfterDelete.isEmpty())
    }

}
