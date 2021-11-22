package sharedTest

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
fun Any.parseJsonObjectFromFile(path: String): JsonObject =
    javaClass.getResource(path)!!.openStream().use(Json::decodeFromStream)

