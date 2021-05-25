package com.nemesis.rio.data.mplus.ranks

import com.nemesis.rio.data.ranks.serialization.RanksSerialization
import com.nemesis.rio.domain.ranks.Ranks
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RanksSerializationTest {

    @Test
    fun parseNotZeroRanks() {
        val ranksJsonObject = buildJsonObject {
            put("world", JsonPrimitive(3))
            put("region", JsonPrimitive(2))
            put("realm", JsonPrimitive(1))
        }

        val expectedRanks = Ranks(3, 2, 1)

        val result = RanksSerialization.parseRanksOrNull(ranksJsonObject)

        assertEquals(expectedRanks, result)
    }

    @Test
    fun parseZeroRanks() {
        val ranksJsonObject = buildJsonObject {
            put("world", JsonPrimitive(0))
            put("region", JsonPrimitive(0))
            put("realm", JsonPrimitive(0))
        }

        val result = RanksSerialization.parseRanksOrNull(ranksJsonObject)

        assertTrue(result == null)
    }
}
