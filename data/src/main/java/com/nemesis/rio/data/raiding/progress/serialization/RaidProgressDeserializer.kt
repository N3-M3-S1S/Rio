package com.nemesis.rio.data.raiding.progress.serialization

import com.nemesis.rio.data.profile.api.ProfileSearchFields
import com.nemesis.rio.data.raiding.serialization.DifficultySerialization
import com.nemesis.rio.data.raiding.serialization.RaidSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.Kills
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.utils.enumMap
import kotlinx.serialization.json.*
import timber.log.Timber

@OptIn(ExperimentalStdlibApi::class)
object RaidProgressDeserializer : JsonObjectDeserializer<Map<Raid, RaidProgress>>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): Map<Raid, RaidProgress> {
        val raidProgress = enumMap<Raid, RaidProgress>()
        val raidProgressJsonObject = getRaidProgressionJsonObject(jsonObject)

        for ((raidJsonValue, raidProgressElement) in raidProgressJsonObject) {
            val raid = parseRaidOrNull(raidJsonValue) ?: continue
            val progressForRaid =
                parseRaidProgressOrNull(raidProgressElement) ?: continue
            raidProgress[raid] = progressForRaid
        }

        return raidProgress
    }

    private fun getRaidProgressionJsonObject(jsonObject: JsonObject) =
        jsonObject.getValue(ProfileSearchFields.RAID_PROGRESSION).jsonObject

    private fun parseRaidOrNull(raidJsonValue: String): Raid? {
        val raid = RaidSerialization.parseRaidByJsonValueOrNull(raidJsonValue)
        if (raid == null) {
            Timber.w("Unknown raid json value: $raidJsonValue")
        }
        return raid
    }

    private fun parseRaidProgressOrNull(raidProgressElement: JsonElement): RaidProgress? {
        val raidProgress = enumMap<Difficulty, Int>()

        enumValues<Difficulty>().forEach { difficulty ->
            val killsForDifficulty = parseKillForDifficulty(difficulty, raidProgressElement)
            if (killsForDifficulty > 0) {
                raidProgress[difficulty] = killsForDifficulty
            }
        }

        return raidProgress.takeIf { it.values.sum() > 0 }
    }

    private fun parseKillForDifficulty(
        difficulty: Difficulty,
        raidProgressElement: JsonElement
    ): Kills {
        val difficultyJsonValue =
            DifficultySerialization.getRaidProgressDifficultyJsonValue(difficulty)
        return raidProgressElement.jsonObject.getValue(difficultyJsonValue).jsonPrimitive.int
    }
}
