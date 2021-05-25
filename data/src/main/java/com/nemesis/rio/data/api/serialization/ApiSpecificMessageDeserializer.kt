package com.nemesis.rio.data.api.serialization

import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.data.serialization.getUnquotedString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

object ApiSpecificMessageDeserializer : JsonObjectDeserializer<String>() {

    override fun deserialize(jsonObject: JsonObject, json: Json): String {
        return jsonObject.getUnquotedString("message")
    }
}
