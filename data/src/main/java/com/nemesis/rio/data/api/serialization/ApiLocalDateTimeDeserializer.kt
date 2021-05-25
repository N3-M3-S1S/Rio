package com.nemesis.rio.data.api.serialization

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
internal object ApiLocalDateTimeDeserializer : KSerializer<LocalDateTime> {

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return decoder.decodeSerializableValue(Instant.serializer())
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) =
        throw UnsupportedOperationException()

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ApiLocalDateTime", PrimitiveKind.STRING)
}
