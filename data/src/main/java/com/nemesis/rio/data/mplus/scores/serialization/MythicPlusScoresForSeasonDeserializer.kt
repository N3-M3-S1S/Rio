package com.nemesis.rio.data.mplus.scores.serialization

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.character.serialization.attributes.RoleSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.utils.enumMap
import kotlinx.serialization.json.*

class MythicPlusScoresForSeasonDeserializer(private val characterClass: CharacterClass) :
    JsonObjectDeserializer<List<MythicPlusScoresContainer>>() {

    @OptIn(ExperimentalStdlibApi::class)
    override fun deserialize(
        jsonObject: JsonObject,
        json: Json,
    ): List<MythicPlusScoresContainer> {
        val scoresBySeasonJsonObjects = getScoresBySeasonJsonObjects(jsonObject)

        val result = mutableListOf<MythicPlusScoresContainer>()

        for (scoresBySeasonJsonObject in scoresBySeasonJsonObjects) {
            val scoresJsonObject = getScoresJsonObject(scoresBySeasonJsonObject)
            val overallScore = parseOverallScore(scoresJsonObject)
            if (overallScore == 0f) continue // if overall score is 0 - a character has no scores for a season, no need to parse further
            val seasonApiValue = getSeasonJsonValue(scoresBySeasonJsonObject)
            val roleScores = parseRoleScores(scoresJsonObject)
            val specScores = parseSpecScores(scoresJsonObject)
            val mythicPlusScoresForSeason =
                MythicPlusScoresContainer(seasonApiValue, overallScore, roleScores, specScores)
            result.add(mythicPlusScoresForSeason)
        }

        return result
    }

    private fun getScoresBySeasonJsonObjects(jsonObject: JsonObject): List<JsonObject> =
        jsonObject.getValue(CharacterSearchFields.MYTHIC_PLUS_SCORES_BY_SEASON)
            .jsonArray
            .map { it.jsonObject }

    private fun getScoresJsonObject(scoresBySeasonJsonObject: JsonObject): JsonObject =
        scoresBySeasonJsonObject.getValue("scores").jsonObject

    private fun parseOverallScore(scoresJsonObject: JsonObject): MythicPlusScore =
        parseMythicPlusScore(scoresJsonObject, "all")

    private fun parseRoleScores(scoresJsonObject: JsonObject): Map<Role, MythicPlusScore> {
        val roleScores = enumMap<Role, MythicPlusScore>()
        characterClass.roles.forEach { role ->
            val roleScoreKey = RoleSerialization.roleToJsonValue.getValue(role)
            val roleScore = parseMythicPlusScore(scoresJsonObject, roleScoreKey)
            if (roleScore != 0F) {
                roleScores[role] = roleScore
            }
        }
        return roleScores
    }

    private fun parseSpecScores(scoresJsonObject: JsonObject): Map<Spec, MythicPlusScore> {
        val specScores = enumMap<Spec, MythicPlusScore>()
        characterClass.specs.forEachIndexed { index, spec ->
            val specScoreKey = "spec_$index"
            val scoreForSpec = parseMythicPlusScore(scoresJsonObject, specScoreKey)
            if (scoreForSpec != 0F) {
                specScores[spec] = scoreForSpec
            }
        }
        return specScores
    }

    private fun parseMythicPlusScore(
        scoresJsonObject: JsonObject,
        scoreKey: String
    ): MythicPlusScore =
        scoresJsonObject.getValue(scoreKey).jsonPrimitive.float

    private fun getSeasonJsonValue(scoresBySeasonJsonObject: JsonObject): String =
        scoresBySeasonJsonObject.getUnquotedString("season")
}
