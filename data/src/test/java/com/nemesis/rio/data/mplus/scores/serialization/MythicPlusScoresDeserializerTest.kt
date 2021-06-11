package com.nemesis.rio.data.mplus.scores.serialization

import com.nemesis.rio.domain.profile.character.attributes.Role
import kotlinx.serialization.json.Json
import org.junit.Test
import sharedTest.parseJsonObjectFromFile
import kotlin.test.assertEquals

internal class MythicPlusScoresDeserializerTest {
    private val mythicPlusScoresJson = parseJsonObjectFromFile("/json/mplus/mplus_scores_json")

    @Test
    fun verifyParsedScores() {
        val expectedSeasonApiValue = "season-bfa-1"

        val result = Json.decodeFromJsonElement(
            MythicPlusScoresForSeasonDeserializer,
            mythicPlusScoresJson
        )

        assertEquals(setOf(expectedSeasonApiValue), result.mapTo(mutableSetOf(),MythicPlusScoresContainer::seasonApiValue))

        val scores = result.first { it.seasonApiValue == expectedSeasonApiValue }

        val expectedOverallScore = 1838F
        assertEquals(expectedOverallScore, scores.overallScore)

        val expectedRoleScores = mapOf(Role.DAMAGE_DEALER to 1599.3F, Role.TANK to 1727.6F)
        assertEquals(expectedRoleScores, scores.roleScores)
    }
}
