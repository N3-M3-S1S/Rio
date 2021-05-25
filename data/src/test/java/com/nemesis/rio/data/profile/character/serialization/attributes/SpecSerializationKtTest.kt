package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.CharacterClass
import com.nemesis.rio.domain.profile.character.attributes.Spec
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecSerializationKtTest {

    @Test
    fun parseSpecWithValueAndClass() {
        val characterClass = CharacterClass.DRUID
        val specJsonValue = "Restoration"

        val result = SpecSerialization.parseSpecByJsonValue(characterClass, specJsonValue)

        assertEquals(Spec.RESTORATION_DRUID, result)
    }

    @Test
    fun parseSpecWithSpace() {
        val specJsonValue = "Beast mastery"

        val result = SpecSerialization.parseSpecByJsonValue(CharacterClass.HUNTER, specJsonValue)

        assertEquals(Spec.BEAST_MASTERY, result)
    }

    @Test
    fun testParseSpec() {
        val specJsonValue = "fury"

        val result = SpecSerialization.parseSpecByJsonValue(CharacterClass.WARRIOR, specJsonValue)

        assertEquals(Spec.FURY, result)
    }
}
