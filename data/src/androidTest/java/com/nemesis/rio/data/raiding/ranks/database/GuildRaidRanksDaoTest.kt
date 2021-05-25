package com.nemesis.rio.data.raiding.ranks.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestGuildInDatabase
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.ranks.Ranks
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class GuildRaidRanksDaoTest : AppDatabaseTest() {
    private val raidRanksDao = appDatabase.raidRanksDao
    private val testRaidRanks = mapOf(
        Difficulty.NORMAL to Ranks(1, 2, 3),
        Difficulty.HEROIC to Ranks(4, 5, 6),
        Difficulty.MYTHIC to Ranks(7, 8, 9)
    )

    @Test
    fun saveAndGetAllRanks() = runBlocking {
        val guildID = createTestGuildInDatabase()
        val expectedRaidRanks = enumValues<Raid>().associateWith { testRaidRanks }

        raidRanksDao.saveOrUpdate(expectedRaidRanks.toRaidRanksEntities(guildID))

        val result = raidRanksDao.getAllRaidRanksEntities(guildID).toRaidWithRanks()

        assertEquals(expectedRaidRanks, result)
    }

    @Test
    fun updateAndGetRanks() = runBlocking {
        val guildID = createTestGuildInDatabase()
        val testRaid = Raid.THE_NIGHTHOLD
        val expectedRaidRanks = mutableMapOf(testRaid to testRaidRanks)

        raidRanksDao.saveOrUpdate(expectedRaidRanks.toRaidRanksEntities(guildID))
        expectedRaidRanks[testRaid] = testRaidRanks.toMutableMap().apply {
            put(Difficulty.MYTHIC, Ranks(10, 11, 12))
        }
        raidRanksDao.saveOrUpdate(expectedRaidRanks.toRaidRanksEntities(guildID))

        val result = raidRanksDao.getRaidRanksEntitiesForRaid(testRaid, guildID).toRaidRanks()

        assertEquals(expectedRaidRanks[testRaid], result)
    }

    @Test
    fun deleteIfRaidIsNotInList() = runBlocking {
        val guildID = createTestGuildInDatabase()
        val expectedRaids = listOf(Raid.CRUCIBLE_OF_STORMS, Raid.THE_EMERALD_NIGHTMARE)
        val raidRanksEntities =
            enumValues<Raid>().associateWith { testRaidRanks }.toRaidRanksEntities(guildID)

        raidRanksDao.saveOrUpdate(raidRanksEntities)
        raidRanksDao.deleteRanksIfRaidIsNotInList(expectedRaids, guildID)

        val result = raidRanksDao.getAllRaidRanksEntities(guildID).map { it.raid }.toSet()

        assertEquals(expectedRaids.toSet(), result)
    }

}
