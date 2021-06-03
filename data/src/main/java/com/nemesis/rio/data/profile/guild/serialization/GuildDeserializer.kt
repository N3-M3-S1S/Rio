package com.nemesis.rio.data.profile.guild.serialization

import com.nemesis.rio.data.profile.serialization.ProfileSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.domain.profile.Guild
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

object GuildDeserializer : JsonObjectDeserializer<Guild>() {

    override fun deserialize(jsonObject: JsonObject, json: Json) = with(jsonObject) {
        val name = ProfileSerialization.parseName(jsonObject)
        val region = ProfileSerialization.parseRegion(jsonObject)
        val faction = ProfileSerialization.parseFaction(jsonObject)
        val url = ProfileSerialization.parseUrl(jsonObject)
        val realm = ProfileSerialization.parseRealm(jsonObject)
        Guild(name, region, faction, url, realm)
    }
}
