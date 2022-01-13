package com.nemesis.rio.data.raiding.achievements.serialization

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.raiding.serialization.RaidSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.AheadOfTheCurve
import com.nemesis.rio.domain.raiding.achievements.CuttingEdge
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.utils.enumMap
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import timber.log.Timber

object RaidAchievementsDeserializer : JsonObjectDeserializer<Map<Raid, List<RaidAchievement>>>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): Map<Raid, List<RaidAchievement>> {
        val raidAchievementsJsonObjects = getRaidAchievementsJsonObjects(jsonObject)
        return if (characterHasRaidAchievements(raidAchievementsJsonObjects)) {
            parseRaidsWithAchievements(raidAchievementsJsonObjects)
        } else {
            emptyMap()
        }
    }

    private fun getRaidAchievementsJsonObjects(jsonObject: JsonObject): List<JsonObject> =
        jsonObject.getValue(CharacterSearchFields.RAID_ACHIEVEMENT_CURVE).jsonArray
            .map { it.jsonObject }

    private fun characterHasRaidAchievements(raidAchievementsJsonObjects: List<JsonObject>): Boolean =
        raidAchievementsJsonObjects.isNotEmpty()

    private fun parseRaidsWithAchievements(raidAchievementsJsonObjects: List<JsonObject>): Map<Raid, List<RaidAchievement>> {
        val raidsWithAchievements = enumMap<Raid, List<RaidAchievement>>()
        for (raidAchievementJsonObject in raidAchievementsJsonObjects) {
            val raid = parseRaidOrNull(raidAchievementJsonObject) ?: continue
            val raidAchievements = parseRaidAchievements(raidAchievementJsonObject)
            raidsWithAchievements[raid] = raidAchievements
        }
        return raidsWithAchievements
    }

    private fun parseRaidOrNull(raidAchievementsJsonObject: JsonObject): Raid? {
        val raidJsonValue = raidAchievementsJsonObject.getUnquotedString("raid")
        val raid = RaidSerialization.parseRaidByJsonValueOrNull(raidJsonValue)
        if (raid == null) {
            Timber.w("Unknown raid json value: $raidJsonValue")
        }
        return raid
    }

    private fun parseRaidAchievements(raidAchievementsJsonObject: JsonObject): List<RaidAchievement> {
        val raidAchievements = mutableListOf<RaidAchievement>()
        val aheadOfTheCurve =
            parseRaidAchievement("aotc", raidAchievementsJsonObject, ::AheadOfTheCurve)
        if (aheadOfTheCurve != null) { // if a character doesn't have ahead of the curve, he can't have cutting edge
            raidAchievements.add(aheadOfTheCurve)
            val cuttingEdge =
                parseRaidAchievement("cutting_edge", raidAchievementsJsonObject, ::CuttingEdge)
            cuttingEdge?.let(raidAchievements::add)
        }
        return raidAchievements
    }

    private fun parseRaidAchievement(
        raidAchievementKey: String,
        raidAchievementsJsonObject: JsonObject,
        raidAchievementBuilder: (Instant) -> RaidAchievement
    ): RaidAchievement? {
        val raidAchievementDateTime =
            parseRaidAchievementAchievementInstant(raidAchievementKey, raidAchievementsJsonObject)
                ?: return null
        return raidAchievementBuilder(raidAchievementDateTime)
    }

    private fun parseRaidAchievementAchievementInstant(
        raidAchievementKey: String,
        raidAchievementsJsonObject: JsonObject
    ): Instant? = raidAchievementsJsonObject[raidAchievementKey]?.let {
        Json.decodeFromJsonElement(Instant.serializer(), it)
    }
}
