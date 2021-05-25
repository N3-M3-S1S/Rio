package sharedTest

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

fun Any.parseJsonObjectFromFile(path: String) =
    javaClass.getResource(path)!!.readText().parseJson()

private fun String.parseJson() = Json.decodeFromString(JsonObject.serializer(), this)
