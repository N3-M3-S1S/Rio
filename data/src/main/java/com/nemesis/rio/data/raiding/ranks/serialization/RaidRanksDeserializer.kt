package com.nemesis.rio.data.raiding.ranks.serialization

import com.nemesis.rio.data.profile.guild.api.GuildSearchFields
import com.nemesis.rio.data.raiding.serialization.DifficultySerialization
import com.nemesis.rio.data.raiding.serialization.RaidSerialization
import com.nemesis.rio.data.ranks.serialization.RanksSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.ranks.RaidRanks
import com.nemesis.rio.domain.ranks.Ranks
import com.nemesis.rio.utils.enumMap
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import timber.log.Timber

object RaidRanksDeserializer : JsonObjectDeserializer<Map<Raid, RaidRanks>>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): Map<Raid, RaidRanks> {
        val rootRaidRanksJsonObject = getRootRaidRanksJsonObject(jsonObject)
        val raidRanks = enumMap<Raid, RaidRanks>()

        for ((raidJsonValue, raidRanksElement) in rootRaidRanksJsonObject) {
            val raid = parseRaidOrNull(raidJsonValue) ?: continue
            val ranks = parseRaidRanksOrNull(raidRanksElement) ?: continue
            raidRanks[raid] = ranks
        }

        return raidRanks
    }

    private fun getRootRaidRanksJsonObject(jsonObject: JsonObject) =
        jsonObject.getValue(GuildSearchFields.RAID_RANKINGS).jsonObject

    private fun parseRaidOrNull(raidJsonValue: String): Raid? {
        val raid = RaidSerialization.parseRaidByJsonValueOrNull(raidJsonValue)
        if (raid == null) {
            Timber.w("Unknown raid json value: $raidJsonValue")
        }
        return raid
    }

    private fun parseRaidRanksOrNull(raidRanksElement: JsonElement): RaidRanks? {
        val raidRanks = enumMap<Difficulty, Ranks>()

        enumValues<Difficulty>().forEach { difficulty ->
            val ranksForDifficulty = parseRanksForDifficulty(difficulty, raidRanksElement)
            if (ranksForDifficulty != null) {
                raidRanks[difficulty] = ranksForDifficulty
            }
        }

        return raidRanks.takeIf { it.isNotEmpty() }
    }

    private fun parseRanksForDifficulty(
        difficulty: Difficulty,
        raidRanksElement: JsonElement
    ): Ranks? {
        val ranksJsonObjectForDifficulty =
            getRanksJsonObjectForDifficulty(difficulty, raidRanksElement)
        return RanksSerialization.parseRanksOrNull(ranksJsonObjectForDifficulty)
    }

    private fun getRanksJsonObjectForDifficulty(
        difficulty: Difficulty,
        raidRanksElement: JsonElement
    ): JsonObject {
        val difficultyJsonValue =
            DifficultySerialization.getRaidRanksDifficultyJsonValue(difficulty)
        return raidRanksElement.jsonObject.getValue(difficultyJsonValue).jsonObject
    }
}
