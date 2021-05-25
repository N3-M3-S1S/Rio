package com.nemesis.rio.data.mplus.runs.serialization

import com.nemesis.rio.data.api.serialization.ApiLocalDateTimeDeserializer
import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.domain.mplus.Affix
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.utils.enumMap
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.*
import timber.log.Timber
import java.time.Duration

object MythicPlusRunsDeserializer : JsonObjectDeserializer<List<MythicPlusRun>>() {

    @OptIn(ExperimentalStdlibApi::class)
    override fun deserialize(jsonObject: JsonObject, json: Json): List<MythicPlusRun> = buildSet {
        addAll(parseRecentRuns(jsonObject))
        addAll(parseBestRuns(jsonObject))
    }.toList() // same run can be both recent and best, so first make a set to exclude duplicates and then make a list that will contain only unique runs

    private fun parseRecentRuns(jsonObject: JsonObject): List<MythicPlusRun> =
        parseRuns(jsonObject, CharacterSearchFields.MYTHIC_PLUS_RECENT_RUNS)

    private fun parseBestRuns(jsonObject: JsonObject): List<MythicPlusRun> =
        parseRuns(jsonObject, CharacterSearchFields.MYTHIC_PLUS_BEST_RUNS)

    private fun parseRuns(
        jsonObject: JsonObject,
        runsField: String
    ): List<MythicPlusRun> = jsonObject
        .getValue(runsField)
        .jsonArray
        .mapNotNull { runElement -> parseRun(runElement.jsonObject) }

    private fun parseRun(mythicPlusRunJsonObject: JsonObject): MythicPlusRun? {
        val dungeon = parseDungeonOrNull(mythicPlusRunJsonObject) ?: return null
        val keystoneLevel = parseKeystoneLevel(mythicPlusRunJsonObject)
        val completeDate = parseCompleteDate(mythicPlusRunJsonObject)
        val duration = parseDuration(mythicPlusRunJsonObject)
        val keystoneUpgrades = parseKeystoneUpgrades(mythicPlusRunJsonObject)
        val score = parseScore(mythicPlusRunJsonObject)
        val affixes = parseAffixes(mythicPlusRunJsonObject)
        val url = parseUrl(mythicPlusRunJsonObject)
        return MythicPlusRun(
            dungeon,
            keystoneLevel,
            completeDate,
            duration,
            keystoneUpgrades,
            score,
            affixes,
            url
        )
    }

    private fun parseDungeonOrNull(mythicPlusRunJsonObject: JsonObject): Dungeon? {
        val dungeonJsonValue = mythicPlusRunJsonObject.getUnquotedString("short_name")
        val dungeon = DungeonSerialization.parseDungeonByJsonValueOrNull(dungeonJsonValue)
        if (dungeon == null) {
            Timber.w("Unknown dungeon json value: $dungeonJsonValue")
        }
        return dungeon
    }

    private fun parseKeystoneLevel(mythicPlusRunJsonObject: JsonObject): Int =
        mythicPlusRunJsonObject.getValue("mythic_level").jsonPrimitive.int

    private fun parseCompleteDate(mythicPlusRunJsonObject: JsonObject): LocalDateTime =
        Json.decodeFromJsonElement(
            ApiLocalDateTimeDeserializer,
            mythicPlusRunJsonObject.getValue("completed_at")
        )

    private fun parseDuration(mythicPlusRunJsonObject: JsonObject): Duration =
        Duration.ofMillis(mythicPlusRunJsonObject.getValue("clear_time_ms").jsonPrimitive.long)

    private fun parseKeystoneUpgrades(mythicPlusRunJsonObject: JsonObject): Int =
        mythicPlusRunJsonObject.getValue("num_keystone_upgrades").jsonPrimitive.int

    private fun parseScore(mythicPlusRunJsonObject: JsonObject): MythicPlusScore =
        mythicPlusRunJsonObject.getValue("score").jsonPrimitive.float

    private fun parseAffixes(mythicPlusRunJsonObject: JsonObject): Map<Affix.Tier, Affix> =
        parseAffixesIds(mythicPlusRunJsonObject).mapNotNull { affixId ->
            val affix = AffixSerialization.parseAffixByIdOrNull(affixId)
            if (affix == null) {
                Timber.w("Unknown affix id: $affixId")
            }
            affix
        }.associateByTo(enumMap(), Affix::tier)

    private fun parseAffixesIds(mythicPlusRunJsonObject: JsonObject): List<Int> =
        getAffixesElementsArray(mythicPlusRunJsonObject).mapNotNull { affixElement ->
            affixElement.jsonObject.getValue("id").jsonPrimitive.int
        }

    private fun getAffixesElementsArray(mythicPlusRunJsonObject: JsonObject): JsonArray =
        mythicPlusRunJsonObject.getValue("affixes").jsonArray

    private fun parseUrl(mythicPlusRunJsonObject: JsonObject): String =
        mythicPlusRunJsonObject.getUnquotedString("url")
}
