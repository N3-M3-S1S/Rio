package com.nemesis.rio.data.raiding.ranks.serialization

import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test
import sharedTest.parseJsonObjectFromFile
import kotlin.test.assertEquals

internal class RaidRanksDeserializerTest {
    private val raidRanksJsonObject = parseJsonObjectFromFile("/json/raiding/raid_ranks_json")
    private val raidRanksParseResult = parseRaidRanks()

    private fun parseRaidRanks() = Json.decodeFromJsonElement(
        RaidRanksDeserializer,
        raidRanksJsonObject
    )

    @Test
    fun resultContainsOnlyRaidsWithRanks() {
        val expectedRaidsWithRanks = setOf(Raid.NYALOTHA_THE_WAKING_CITY, Raid.ULDIR)

        val result = raidRanksParseResult.keys

        assertEquals(expectedRaidsWithRanks, result)
    }

    @Test
    fun verifyRanksForAllDifficulties() {
        val nyalothaRanks = raidRanksParseResult.getValue(Raid.NYALOTHA_THE_WAKING_CITY)

        val normalRanks = nyalothaRanks.getValue(Difficulty.NORMAL)
        with(normalRanks) {
            assertTrue(realm == 20)
            assertTrue(region == 564)
            assertTrue(world == 799)
        }

        val heroicRanks = nyalothaRanks.getValue(Difficulty.HEROIC)
        with(heroicRanks) {
            assertTrue(world == 658)
            assertTrue(region == 521)
            assertTrue(realm == 25)
        }

        val mythicRanks = nyalothaRanks.getValue(Difficulty.MYTHIC)
        with(mythicRanks) {
            assertTrue(world == 123)
            assertTrue(region == 456)
            assertTrue(realm == 789)
        }
    }

    @Test
    fun verifyRanksWithOneDifficulty() {
        val uldirRanks = raidRanksParseResult.getValue(Raid.ULDIR)
        assertTrue(uldirRanks.size == 1)

        val normalRanks = uldirRanks.getValue(Difficulty.NORMAL)
        with(normalRanks) {
            assertTrue(world == 799)
            assertTrue(region == 564)
            assertTrue(realm == 20)
        }
    }
}
