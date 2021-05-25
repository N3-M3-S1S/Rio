package com.nemesis.rio.data.api

import com.nemesis.rio.data.api.serialization.ApiLocalDateTimeDeserializer
import com.nemesis.rio.utils.now
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ApiLocalDateTimeDeserializerTest {

    @BeforeTest
    fun setup() {
        mockkObject(TimeZone.Companion) {
            every { TimeZone.Companion.currentSystemDefault() } returns TimeZone.UTC
        }
    }

    @Test
    fun deserialize() {
        val dateTimeKey = "dateTime"
        val expectedDateTime = LocalDateTime(2019, 9, 28, 20, 2, 0)
        val expectedDateTimeStringValue =
            expectedDateTime.toInstant(TimeZone.currentSystemDefault()).toString()
        val jsonObject = buildJsonObject { put(dateTimeKey, expectedDateTimeStringValue) }

        val result = Json.decodeFromJsonElement(
            ApiLocalDateTimeDeserializer,
            jsonObject.getValue(dateTimeKey)
        )

        assertEquals(expectedDateTime, result)
    }

    @Test
    fun serialize() {
        assertFailsWith<UnsupportedOperationException> {
            Json.encodeToString(ApiLocalDateTimeDeserializer, LocalDateTime.now())
        }
    }
}
