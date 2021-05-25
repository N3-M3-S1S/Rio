package com.nemesis.rio.data.mplus.ranks.serialization

import com.nemesis.rio.domain.mplus.ranks.MythicPlusClassRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusOverallRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.domain.ranks.Ranks
import kotlinx.serialization.json.*
import sharedTest.parseJsonObjectFromFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class MythicPlusRanksDeserializerTest {
    private val mythicPlusRanksJson = parseJsonObjectFromFile("/json/mplus/mplus_ranks_json")
    private val mythicPlusRanksParseResult = parseMythicPlusRanks(mythicPlusRanksJson)

    private fun parseMythicPlusRanks(mythicPlusRanksJson: JsonElement) =
        Json.decodeFromJsonElement(MythicPlusRanksDeserializer, mythicPlusRanksJson)

    @Test
    fun `exception is thrown if there is unknown spec`() {
        val mythicPlusRanksJsonContentWithUnknownSpecValue =
            mythicPlusRanksJson.getValue("mythic_plus_ranks").jsonObject.toMutableMap()
                .apply { put("spec_999", JsonNull) }
        assertFails {
            val ranksJson = buildJsonObject {
                put("mythic_plus_ranks", JsonObject(mythicPlusRanksJsonContentWithUnknownSpecValue))
            }
            parseMythicPlusRanks(ranksJson)
        }
    }

    // class ranks contains DPS role with zero ranks, so it must not be in result
    @Test
    fun verifyGlobalClassRanks() {
        val expectedRanks = Ranks(14179, 8857, 401)
        val expectedRoleRanks = mapOf(Role.TANK to Ranks(6469, 4181, 198))
        val expectedClassRanks = MythicPlusClassRanks(expectedRanks, expectedRoleRanks)

        val resultClassRanks =
            mythicPlusRanksParseResult.getValue(MythicPlusRanksScope.GLOBAL).classRanks

        assertEquals(expectedClassRanks, resultClassRanks)
    }

    @Test
    fun verifyFactionClassRanks() {
        val expectedRanks = Ranks(4471, 2712, 391)
        val expectedRoleRanks = mapOf(Role.TANK to Ranks(1891, 1165, 196))
        val expectedClassRanks = MythicPlusClassRanks(expectedRanks, expectedRoleRanks)

        val resultClassRanks =
            mythicPlusRanksParseResult.getValue(MythicPlusRanksScope.FACTION).classRanks

        assertEquals(expectedClassRanks, resultClassRanks)
    }

    @Test
    fun verifyGlobalOverallRanks() {
        val expectedRanks = Ranks(213104, 128634, 6030)
        val expectedRoleRanks = mapOf(Role.HEALER to Ranks(42770, 25872, 1242))
        val expectedOverallRanks = MythicPlusOverallRanks(expectedRanks, expectedRoleRanks)

        val resultOverallRanks =
            mythicPlusRanksParseResult.getValue(MythicPlusRanksScope.GLOBAL).overallRanks

        assertEquals(expectedOverallRanks, resultOverallRanks)
    }

    @Test
    fun verifyFactionOverallRanks() {
        val expectedRanks = Ranks(67365, 39616, 5896)
        val expectedRoleRanks = mapOf(Role.HEALER to Ranks(261000, 145267, 13250))
        val expectedOverallRanks = MythicPlusOverallRanks(expectedRanks, expectedRoleRanks)

        val resultOverallRanks =
            mythicPlusRanksParseResult.getValue(MythicPlusRanksScope.FACTION).overallRanks

        assertEquals(expectedOverallRanks, resultOverallRanks)
    }

    // Global spec ranks contains a spec with unknown id
    @Test
    fun verifyGlobalSpecRanks() {
        val expectedSpecRanks = mapOf(Spec.BREWMASTER to Ranks(6469, 4181, 198))

        val resultSpecRanks =
            mythicPlusRanksParseResult.getValue(MythicPlusRanksScope.GLOBAL).specRanks

        assertEquals(expectedSpecRanks, resultSpecRanks)
    }

    @Test
    fun verifyFactionSpecRanks() {
        val resultSpecRanks =
            mythicPlusRanksParseResult.getValue(MythicPlusRanksScope.FACTION).specRanks

        val expectedSpecRanks = mapOf(Spec.WINDWALKER to Ranks(1891, 1165, 196))

        assertEquals(expectedSpecRanks, resultSpecRanks)
    }
}
