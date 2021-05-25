package com.nemesis.rio.data.raiding.achievements.serialization

import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.AheadOfTheCurve
import com.nemesis.rio.domain.raiding.achievements.CuttingEdge
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test
import sharedTest.parseJsonObjectFromFile
import kotlin.test.assertEquals

internal class RaidAchievementsDeserializerTest {
    private val raidAchievementsJson =
        parseJsonObjectFromFile("/json/raiding/raid_achievements_json")
    private val raidAchievementsParseResult = parseRaidAchievements()

    private fun parseRaidAchievements() =
        Json.decodeFromJsonElement(RaidAchievementsDeserializer, raidAchievementsJson)

    @Test
    fun resultContainsExpectedRaids() {
        val expectedRaidsWithAchievements = setOf(Raid.ULDIR, Raid.THE_ETERNAL_PALACE)

        val result = raidAchievementsParseResult.keys

        assertEquals(expectedRaidsWithAchievements, result)
    }

    @Test
    fun verifyRaidWithOnlyAheadOfTheCurve() {
        val eternalPalaceAchievements =
            raidAchievementsParseResult.getValue(Raid.THE_ETERNAL_PALACE)

        assertTrue(eternalPalaceAchievements.size == 1 && eternalPalaceAchievements.first() is AheadOfTheCurve)
    }

    @Test
    fun verifyRaidWithAllAchievements() {
        val uldirAchievements = raidAchievementsParseResult.getValue(Raid.ULDIR)

        assertTrue(uldirAchievements.size == 2)
        assertTrue(uldirAchievements.any { it is AheadOfTheCurve } && uldirAchievements.any { it is CuttingEdge })
    }
}
