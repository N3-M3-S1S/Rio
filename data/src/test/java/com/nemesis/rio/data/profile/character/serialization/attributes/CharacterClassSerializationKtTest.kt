package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterClassSerializationKtTest {

    @Test
    fun parseClassWithSpace() {
        val classJsonValue = "Death knight"

        val result = CharacterClassSerialization.parseCharacterClassByJsonValue(classJsonValue)

        assertEquals(CharacterClass.DEATH_KNIGHT, result)
    }

    @Test
    fun parseClass() {
        val classJsonValue = "mage"

        val result = CharacterClassSerialization.parseCharacterClassByJsonValue(classJsonValue)

        assertEquals(CharacterClass.MAGE, result)
    }
}
