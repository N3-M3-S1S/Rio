package com.nemesis.rio.data.api.search.serialization

import com.nemesis.rio.data.api.search.CharacterSearchResponse
import com.nemesis.rio.data.mplus.ranks.serialization.MythicPlusRanksDeserializer
import com.nemesis.rio.data.mplus.runs.serialization.MythicPlusRunsDeserializer
import com.nemesis.rio.data.mplus.scores.serialization.MythicPlusSeasonApiValueToScoresDeserializer
import com.nemesis.rio.data.profile.character.serialization.CharacterDeserializer
import com.nemesis.rio.data.raiding.achievements.serialization.RaidAchievementsDeserializer
import com.nemesis.rio.data.raiding.progress.serialization.RaidProgressDeserializer
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = CharacterSearchResponse::class)
internal object CharacterSearchResponseDeserializer : JsonObjectDeserializer<CharacterSearchResponse>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): CharacterSearchResponse {
        fun <T> deserialize(deserializer: DeserializationStrategy<T>) =
            json.decodeFromJsonElement(deserializer, jsonObject)

        val character = deserialize(CharacterDeserializer)
        val mythicPlusScores = deserialize(MythicPlusSeasonApiValueToScoresDeserializer)
        val mythicPlusRanks = deserialize(MythicPlusRanksDeserializer)
        val mythicPlusRuns = deserialize(MythicPlusRunsDeserializer)
        val raidAchievements = deserialize(RaidAchievementsDeserializer)
        val raidProgress = deserialize(RaidProgressDeserializer)

        return CharacterSearchResponse(
            character,
            mythicPlusScores,
            mythicPlusRanks,
            mythicPlusRuns,
            raidAchievements,
            raidProgress
        )
    }
}
