package com.nemesis.rio.data.serialization

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

internal fun JsonObject.getUnquotedString(key: String): String =
    get(key)?.jsonPrimitive?.content ?: missingJsonField(key)

private fun missingJsonField(fieldName: String): Nothing =
    serializationError("Field '$fieldName' is missing in json")

private fun serializationError(message: String): Nothing =
    throw SerializationException(message)
