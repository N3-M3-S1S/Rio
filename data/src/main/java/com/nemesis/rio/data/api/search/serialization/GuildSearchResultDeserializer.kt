package com.nemesis.rio.data.api.search.serialization

import com.nemesis.rio.data.api.search.GuildSearchResponse
import com.nemesis.rio.data.profile.guild.serialization.GuildDeserializer
import com.nemesis.rio.data.raiding.progress.serialization.RaidProgressDeserializer
import com.nemesis.rio.data.raiding.ranks.serialization.RaidRanksDeserializer
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = GuildSearchResponse::class)
internal object GuildSearchResultDeserializer : JsonObjectDeserializer<GuildSearchResponse>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): GuildSearchResponse {
        fun <T> deserialize(deserializer: DeserializationStrategy<T>) =
            json.decodeFromJsonElement(deserializer, jsonObject)

        val guild = deserialize(GuildDeserializer)
        val raidRanks = deserialize(RaidRanksDeserializer)
        val raidProgress = deserialize(RaidProgressDeserializer)

        return GuildSearchResponse(guild, raidRanks, raidProgress)
    }
}
