package com.nemesis.rio.data.profile.character.serialization

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.character.serialization.attributes.CharacterAttributesDeserializer
import com.nemesis.rio.data.profile.character.serialization.gear.GearDeserializer
import com.nemesis.rio.data.profile.serialization.ProfileSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes
import com.nemesis.rio.domain.profile.character.gear.Gear
import kotlinx.serialization.json.*

object CharacterDeserializer : JsonObjectDeserializer<Character>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): Character {
        val name = ProfileSerialization.parseName(jsonObject)
        val region = ProfileSerialization.parseRegion(jsonObject)
        val faction = ProfileSerialization.parseFaction(jsonObject)
        val url = ProfileSerialization.parseUrl(jsonObject)
        val realm = ProfileSerialization.parseRealm(jsonObject)
        val attributes = parseAttributes(jsonObject)
        val gear = parseGear(jsonObject)
        val imageUrl = parseImageUrl(jsonObject)
        val guildName = parseGuildName(jsonObject)

        return Character(
            name, region, faction, url, realm, attributes, gear, imageUrl, guildName
        )
    }

    private fun parseAttributes(characterJsonObject: JsonObject): CharacterAttributes =
        Json.decodeFromJsonElement(CharacterAttributesDeserializer, characterJsonObject)

    private fun parseGear(characterJsonObject: JsonObject): Gear =
        Json.decodeFromJsonElement(GearDeserializer, characterJsonObject)

    private fun parseImageUrl(characterJsonObject: JsonObject): String =
        characterJsonObject.getUnquotedString("thumbnail_url")

    private fun parseGuildName(characterJsonObject: JsonObject): String? {
        val guildJsonElement = getGuildJsonElement(characterJsonObject)
        return if (characterHasGuild(guildJsonElement)) {
            parseGuildName(guildJsonElement)
        } else {
            null
        }
    }

    private fun getGuildJsonElement(characterJsonObject: JsonObject): JsonElement =
        characterJsonObject[CharacterSearchFields.GUILD]
            ?: error("Field ${CharacterSearchFields.GUILD} is missing in json")

    private fun characterHasGuild(guildJsonElement: JsonElement): Boolean =
        guildJsonElement !is JsonNull

    private fun parseGuildName(guildJsonElement: JsonElement): String =
        guildJsonElement.jsonObject.getUnquotedString("name")
}
