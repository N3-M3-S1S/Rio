package com.nemesis.rio.data.raiding.progress.serialization

import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import kotlinx.serialization.json.Json
import org.junit.Test
import sharedTest.parseJsonObjectFromFile
import kotlin.test.assertEquals

internal class RaidProgressDeserializerTest {
    private val raidProgressJsonObject = parseJsonObjectFromFile("/json/raiding/raid_progress_json")
    private var raidProgressParseResult = parseRaidProgress()

    private fun parseRaidProgress() =
        Json.decodeFromJsonElement(RaidProgressDeserializer, raidProgressJsonObject)

    @Test
    fun resultContainsOnlyRaidsWithProgress() {
        val expectedRaidsWithProgress = setOf(Raid.THE_ETERNAL_PALACE, Raid.NYALOTHA_THE_WAKING_CITY)

        val result = raidProgressParseResult.keys

        assertEquals(expectedRaidsWithProgress, result)
    }

    @Test
    fun verifyProgressForAllDifficulties() {
        val expectedProgress =
            mapOf(Difficulty.NORMAL to 8, Difficulty.HEROIC to 8, Difficulty.MYTHIC to 3)

        val result = raidProgressParseResult.getValue(Raid.THE_ETERNAL_PALACE)

        assertEquals(expectedProgress, result)
    }

    @Test
    fun verifyProgressForOneDifficulty() {
        val expectedProgress = mapOf(Difficulty.MYTHIC to 1)

        val result = raidProgressParseResult.getValue(Raid.NYALOTHA_THE_WAKING_CITY)

        assertEquals(expectedProgress, result)
    }
}
