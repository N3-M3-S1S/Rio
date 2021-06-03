package com.nemesis.rio.data.profile.character.serialization

import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.*
import com.nemesis.rio.domain.profile.character.gear.Gear
import com.nemesis.rio.domain.server.Region
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import sharedTest.parseJsonObjectFromFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CharacterDeserializerTest {
    private var characterJson = parseJsonObjectFromFile("/json/profile/character_profile_json")

    @Test
    fun deserialize() {
        val expectedProfile = Character(
            name = "Character",
            region = Region.EU,
            realm = "Gordunni",
            faction = Faction.ALLIANCE,
            attributes = CharacterAttributes(
                characterClass = CharacterClass.MONK,
                activeSpec = Spec.BREWMASTER,
                race = Race.PANDAREN,
                covenant = Covenant.NIGHT_FAE
            ),
            gear = Gear(472),
            imageUrl = "profile_image_url",
            guildName = "guild",
            url = "profile_url"
        )

        val result = Json.decodeFromJsonElement(CharacterDeserializer, characterJson)

        assertEquals(expectedProfile, result)
    }

    @Test
    fun deserializeProfileWithUnknownValue() {
        assertFails {
            val profileInfoJson = characterJson.toMutableMap().apply {
                put("region", JsonPrimitive("unknown"))
            }.let(::JsonObject)
            Json.decodeFromJsonElement(CharacterDeserializer, profileInfoJson)
        }
    }
}
