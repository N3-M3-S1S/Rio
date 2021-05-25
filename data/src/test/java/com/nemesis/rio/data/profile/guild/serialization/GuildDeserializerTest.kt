package com.nemesis.rio.data.profile.guild.serialization

import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.server.Region
import kotlinx.serialization.json.Json
import org.junit.Test
import sharedTest.parseJsonObjectFromFile
import kotlin.test.assertEquals

class GuildDeserializerTest {

    @Test
    fun deserialize() {
        val expectedGuild =
            Guild("Guild name", Region.EU, Faction.HORDE, "profile_url", "Howling Fjord")

        val result = Json.decodeFromJsonElement(
            GuildDeserializer,
            parseJsonObjectFromFile("/json/profile/guild_json")
        )

        assertEquals(expectedGuild, result)
    }
}
