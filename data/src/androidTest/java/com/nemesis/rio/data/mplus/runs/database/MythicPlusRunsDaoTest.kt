package com.nemesis.rio.data.mplus.runs.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.domain.mplus.Affix
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.utils.now
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.junit.Test
import java.time.Duration
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MythicPlusRunsDaoTest : AppDatabaseTest() {
    private val mythicPlusRunsDao = appDatabase.mythicPlusRunsDao


    @Test
    fun saveAndGetRuns() = runBlocking {
        val characterID = createTestCharacterInDatabase()

        val expectedRun1 = MythicPlusRun(
            dungeon = enumValues<Dungeon>().first(),
            keystoneLevel = 1,
            date = LocalDateTime.now(),
            duration = Duration.ofDays(1),
            keystoneUpgrades = 1,
            score = 1f,
            affixes = enumValues<Affix>().groupBy { it.tier }.mapValues { it.value.first() },
            url = "run1"
        )

        val expectedRun2 = expectedRun1.copy(
            dungeon = enumValues<Dungeon>().last(),
            keystoneLevel = 30,
            affixes = expectedRun1.affixes.toMutableMap().apply { remove(Affix.Tier.T4) },
            url = "run2"
        )

        val expectedRuns = listOf(expectedRun1, expectedRun2)

        mythicPlusRunsDao.saveMythicPlusRunPojos(expectedRuns.toMythicPlusRunPojos(characterID))

        val runs = mythicPlusRunsDao.getMythicPlusRunsPojos(characterID).toMythicPlusRuns()
        assertEquals(expectedRuns, runs)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun saveAndDeleteRuns() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val runsCount = 3
        val expectedRuns = buildList {
            for (i in 0 until runsCount)
                add(
                    MythicPlusRun(
                        dungeon = enumValues<Dungeon>().first(),
                        keystoneLevel = 2,
                        date = LocalDateTime.now(),
                        duration = Duration.ofDays(1),
                        keystoneUpgrades = 1,
                        score = 1f,
                        affixes = enumValues<Affix>().groupBy { it.tier }
                            .mapValues { it.value.last() },
                        url = "run$i"
                    )
                )
        }

        mythicPlusRunsDao.saveMythicPlusRunPojos(expectedRuns.toMythicPlusRunPojos(characterID))
        val runs = mythicPlusRunsDao.getMythicPlusRunsPojos(characterID).toMythicPlusRuns()
        assertEquals(expectedRuns, runs)

        mythicPlusRunsDao.deleteAll(characterID)
        val runsAfterDelete =
            mythicPlusRunsDao.getMythicPlusRunsPojos(characterID).toMythicPlusRuns()
        assertTrue(runsAfterDelete.isEmpty())
    }


}
