package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import com.nemesis.rio.utils.searchEnumByName

internal object CharacterClassSerialization {

    // TODO replace with actual values from the api
    internal fun parseCharacterClassByJsonValue(classJsonValue: String): CharacterClass =
        searchEnumByName<CharacterClass>(classJsonValue.replace(" ", "_"))
            ?: error("Unknown class json value: $classJsonValue")
}
