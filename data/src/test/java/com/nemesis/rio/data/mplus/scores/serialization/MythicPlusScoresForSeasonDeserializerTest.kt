package com.nemesis.rio.data.mplus.scores.serialization

import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import kotlinx.serialization.json.Json
import sharedTest.parseJsonObjectFromFile
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MythicPlusScoresForSeasonDeserializerTest {
    private val mythicPlusScoresJson = parseJsonObjectFromFile("/json/mplus/mplus_scores_json")

    @Test
    fun verifyParsedScores() {
        val expectedSeasonApiValue = "season-bfa-1"

        val result = Json.decodeFromJsonElement(
            MythicPlusScoresForSeasonDeserializer(CharacterClass.DEATH_KNIGHT),
            mythicPlusScoresJson
        )

        assertEquals(setOf(expectedSeasonApiValue), result.mapTo(mutableSetOf(), MythicPlusScoresContainer::seasonApiValue))

        val scores = result.first { it.seasonApiValue == expectedSeasonApiValue }

        val expectedOverallScore = 1838F
        assertEquals(expectedOverallScore, scores.overallScore)

        val expectedRoleScores = mapOf(Role.DAMAGE_DEALER to 1599.3F, Role.TANK to 1727.6F)
        assertEquals(expectedRoleScores, scores.roleScores)

        val expectedSpecScores = mapOf(Spec.BLOOD to 100F, Spec.UNHOLY to 300.1F)
        assertEquals(expectedSpecScores, scores.specScores)
    }
}
