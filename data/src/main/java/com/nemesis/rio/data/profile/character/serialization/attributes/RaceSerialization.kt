package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.Race
import com.nemesis.rio.utils.searchEnumByName

internal object RaceSerialization {

    // TODO replace with actual values from the api
    fun parseRaceByJsonValue(raceJsonValue: String) =
        searchEnumByName<Race>(raceJsonValue.replace(" ", "_"))
            ?: error("Unknown race json value: $raceJsonValue")
}
