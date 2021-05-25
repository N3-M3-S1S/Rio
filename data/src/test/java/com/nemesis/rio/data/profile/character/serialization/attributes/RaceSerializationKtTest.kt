package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.Race
import kotlin.test.Test
import kotlin.test.assertEquals

class RaceSerializationKtTest {

    @Test
    fun parseRaceWithSpace() {
        val raceJsonValue = "night elf"

        val result = RaceSerialization.parseRaceByJsonValue(raceJsonValue)

        assertEquals(Race.NIGHT_ELF, result)
    }
}
