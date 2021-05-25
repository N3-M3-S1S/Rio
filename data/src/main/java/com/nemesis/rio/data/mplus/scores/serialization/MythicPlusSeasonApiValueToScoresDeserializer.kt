package com.nemesis.rio.data.mplus.scores.serialization

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.character.serialization.attributes.RoleSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.utils.enumMap
import kotlinx.serialization.json.*

object MythicPlusSeasonApiValueToScoresDeserializer :
    JsonObjectDeserializer<Map<String, MythicPlusScores>>() {

    @OptIn(ExperimentalStdlibApi::class)
    override fun deserialize(
        jsonObject: JsonObject,
        json: Json,
    ): Map<String, MythicPlusScores> {
        val scoresBySeasonJsonObjects = getScoresBySeasonJsonObjects(jsonObject)

        val seasonApiValueWithScores = mutableMapOf<String, MythicPlusScores>()

        for (scoresBySeasonJsonObject in scoresBySeasonJsonObjects) {
            val scoresForSeason =
                parseNotZeroScoresOrNull(getScoresJsonObject(scoresBySeasonJsonObject)) ?: continue
            val seasonApiValue = getSeasonJsonValue(scoresBySeasonJsonObject)
            seasonApiValueWithScores[seasonApiValue] = scoresForSeason
        }

        return seasonApiValueWithScores
    }

    private fun getScoresBySeasonJsonObjects(jsonObject: JsonObject): List<JsonObject> =
        jsonObject.getValue(CharacterSearchFields.MYTHIC_PLUS_SCORES_BY_SEASON)
            .jsonArray
            .map { it.jsonObject }

    private fun getScoresJsonObject(scoresBySeasonJsonObject: JsonObject): JsonObject =
        scoresBySeasonJsonObject.getValue("scores").jsonObject

    @OptIn(ExperimentalStdlibApi::class)
    private fun parseNotZeroScoresOrNull(scoresJsonObject: JsonObject): MythicPlusScores? {
        val overallScore = parseScore(scoresJsonObject, "all")
        return if (overallScore > 0F) {
            val roleScores = parseRoleScores(scoresJsonObject)
            MythicPlusScores(overallScore, roleScores)
        } else {
            null // if overall score if 0, all role scores are also 0
        }
    }

    private fun parseRoleScores(scoresJsonObject: JsonObject): Map<Role, MythicPlusScore> {
        val roleScores = enumMap<Role, MythicPlusScore>()
        RoleSerialization.jsonValueToRole.forEach { (roleJsonValue, role) ->
            val roleScore = parseScore(scoresJsonObject, roleJsonValue)
            if (roleScore != 0F) {
                roleScores[role] = roleScore
            }
        }
        return roleScores
    }

    private fun parseScore(scoresJsonObject: JsonObject, scoreKey: String): MythicPlusScore =
        scoresJsonObject.getValue(scoreKey).jsonPrimitive.float

    private fun getSeasonJsonValue(scoresBySeasonJsonObject: JsonObject): String =
        scoresBySeasonJsonObject.getUnquotedString("season")
}
