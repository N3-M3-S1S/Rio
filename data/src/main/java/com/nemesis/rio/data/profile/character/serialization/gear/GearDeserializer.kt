package com.nemesis.rio.data.profile.character.serialization.gear

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.domain.profile.character.gear.Gear
import kotlinx.serialization.json.*

object GearDeserializer : JsonObjectDeserializer<Gear>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): Gear {
        val gearJsonObject = getGearJsonObject(jsonObject)
        val itemLevel = parseItemLevel(gearJsonObject)
        return Gear(itemLevel)
    }

    private fun getGearJsonObject(characterJsonObject: JsonObject): JsonObject =
        characterJsonObject.getValue(CharacterSearchFields.GEAR).jsonObject

    private fun parseItemLevel(gearJsonObject: JsonObject): Int =
        gearJsonObject.getValue("item_level_equipped").jsonPrimitive.int
}
