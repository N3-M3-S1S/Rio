package com.nemesis.rio.data.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

abstract class JsonObjectDeserializer<T> : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(this::class.simpleName!!) {}

    final override fun deserialize(decoder: Decoder): T {
        require(decoder is JsonDecoder)
        val jsonObject = decoder.decodeJsonElement().jsonObject
        return deserialize(jsonObject, decoder.json)
    }

    abstract fun deserialize(jsonObject: JsonObject, json: Json): T

    final override fun serialize(encoder: Encoder, value: T) = throw UnsupportedOperationException()
}
