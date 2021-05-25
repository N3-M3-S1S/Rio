package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes
import com.nemesis.rio.domain.profile.character.attributes.Covenant
import kotlinx.serialization.json.*

object CharacterAttributesDeserializer : JsonObjectDeserializer<CharacterAttributes>() {

    override fun deserialize(jsonObject: JsonObject, json: Json) = with(jsonObject) {
        val characterClass =
            CharacterClassSerialization.parseCharacterClassByJsonValue(getUnquotedString("class"))
        val activeSpec = SpecSerialization.parseSpecByJsonValue(characterClass, getUnquotedString("active_spec_name"))
        val race = RaceSerialization.parseRaceByJsonValue(getUnquotedString("race"))
        val covenant = parseCovenant(this)
        CharacterAttributes(characterClass, activeSpec, race, covenant)
    }

    private fun parseCovenant(characterJsonObject: JsonObject): Covenant? {
        val covenantJsonElement = characterJsonObject.getValue(CharacterSearchFields.COVENANT)

        return if (covenantJsonElement is JsonNull) { // a character didn't select a covenant
            null
        } else {
            val covenantId = covenantJsonElement.jsonObject.getValue("id").jsonPrimitive.int
            CovenantSerialization.parseCovenantById(covenantId)
        }
    }
}
