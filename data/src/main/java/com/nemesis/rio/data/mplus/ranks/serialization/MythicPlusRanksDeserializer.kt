package com.nemesis.rio.data.mplus.ranks.serialization

import com.nemesis.rio.data.profile.character.api.CharacterSearchFields
import com.nemesis.rio.data.profile.character.serialization.attributes.RoleSerialization
import com.nemesis.rio.data.profile.character.serialization.attributes.SpecSerialization
import com.nemesis.rio.data.ranks.serialization.RanksSerialization
import com.nemesis.rio.data.serialization.JsonObjectDeserializer
import com.nemesis.rio.domain.mplus.ranks.MythicPlusClassRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusOverallRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.MythicPlusSpecRanks
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.domain.ranks.Ranks
import com.nemesis.rio.utils.enumMap
import kotlinx.serialization.json.*

object MythicPlusRanksDeserializer :
    JsonObjectDeserializer<Map<MythicPlusRanksScope, MythicPlusRanksContainer>>() {

    private val specRankKeyRegEx = "^spec_\\d{2,3}\$".toRegex()

    override fun deserialize(
        jsonObject: JsonObject,
        json: Json,
    ): Map<MythicPlusRanksScope, MythicPlusRanksContainer> {
        val rootRanksJsonObject = getRootRanksJsonObject(jsonObject)
        return parseMythicPlusRanks(rootRanksJsonObject)
    }

    private fun getRootRanksJsonObject(jsonObject: JsonObject): JsonObject =
        jsonObject.getValue(CharacterSearchFields.MYTHIC_PLUS_RANKS).jsonObject

    private fun parseMythicPlusRanks(rootRanksJsonObject: JsonObject): Map<MythicPlusRanksScope, MythicPlusRanksContainer> =
        if (characterHasMythicPlusRanks(rootRanksJsonObject)) {
            enumValues<MythicPlusRanksScope>().associateWithTo(enumMap()) { ranksScope ->
                parseMythicPlusRanksForScope(rootRanksJsonObject, ranksScope)
            }
        } else {
            emptyMap()
        }

    // global overall world rank is the most "wide-scoped" rank, if it is zero it means that any other ranks are zero as well
    private fun characterHasMythicPlusRanks(rootRanksJsonObject: JsonObject): Boolean {
        val globalOverallWorldRank =
            rootRanksJsonObject.getValue("overall").jsonObject.getValue("world").jsonPrimitive.int
        return globalOverallWorldRank != 0
    }

    private fun parseMythicPlusRanksForScope(
        rootRanksJsonObject: JsonObject,
        ranksScope: MythicPlusRanksScope,
    ): MythicPlusRanksContainer {
        val ranksObjectForScope = getRanksJsonObjectForScope(rootRanksJsonObject, ranksScope)

        val overallRanks = getOverallRanks(ranksObjectForScope)
        val classRanks = getClassRanks(ranksObjectForScope)
        val specRanks = getSpecRanks(ranksObjectForScope)

        return MythicPlusRanksContainer(classRanks, specRanks, overallRanks)
    }

    private fun getRanksJsonObjectForScope(
        rootRanksJsonObject: JsonObject,
        ranksScope: MythicPlusRanksScope,
    ): JsonObject {
        val factionPrefix = "faction_"
        return when (ranksScope) {
            MythicPlusRanksScope.GLOBAL -> rootRanksJsonObject
                .filterKeys { !it.startsWith(factionPrefix) }
            MythicPlusRanksScope.FACTION -> rootRanksJsonObject
                .filterKeys { it.startsWith(factionPrefix) }
                .mapKeys { it.key.removePrefix(factionPrefix) }
        }.let(::JsonObject)
    }

    private fun getOverallRanks(ranksObject: JsonObject): MythicPlusOverallRanks {
        val overallRank = RanksSerialization.parseRanksOrNull(ranksObject.getValue("overall").jsonObject)
        require(overallRank != null)
        val overallRoleRanks = parseRoleRanks(ranksObject)
        return MythicPlusOverallRanks(overallRank, overallRoleRanks)
    }

    private fun getClassRanks(ranksJsonObject: JsonObject): MythicPlusClassRanks {
        val classRanks = RanksSerialization.parseRanksOrNull(ranksJsonObject.getValue("class").jsonObject)
        require(classRanks != null)
        val classRoleRanksJsonObject = getClassRoleRanksJsonObject(ranksJsonObject)
        val classRoleRanks = parseRoleRanks(classRoleRanksJsonObject)
        return MythicPlusClassRanks(classRanks, classRoleRanks)
    }

    private fun getClassRoleRanksJsonObject(ranksJsonObject: JsonObject): JsonObject {
        val classPrefix = "class_"
        return ranksJsonObject
            .filterKeys { it.startsWith(classPrefix) }
            .mapKeys { it.key.removePrefix(classPrefix) }
            .let(::JsonObject)
    }

    private fun parseRoleRanks(ranksJsonObject: JsonObject): Map<Role, Ranks> {
        val roleRanks = enumMap<Role, Ranks>()
        RoleSerialization.jsonValueToRole.forEach { (apiValue, role) ->
            val ranksForRole =
                ranksJsonObject[apiValue]?.jsonObject?.let(RanksSerialization::parseRanksOrNull)
            if (ranksForRole != null) {
                roleRanks[role] = ranksForRole
            }
        }
        return roleRanks
    }

    private fun getSpecRanks(ranksJsonObject: JsonObject): MythicPlusSpecRanks {
        val specRankKeys = getSpecsRankKeys(ranksJsonObject)
        val result = enumMap<Spec, Ranks>()
        specRankKeys.forEach { specRanksKey ->
            val specId = getSpecIdFromSpecRanksKey(specRanksKey)
            val spec = SpecSerialization.parseSpecById(specId)
            val specRanksObject = ranksJsonObject.getValue(specRanksKey).jsonObject
            val ranks = RanksSerialization.parseRanksOrNull(specRanksObject)
            if (ranks != null) {
                result[spec] = ranks
            }
        }
        return result
    }

    private fun getSpecsRankKeys(ranksJsonObject: JsonObject) =
        ranksJsonObject.keys.filter(specRankKeyRegEx::matches)

    private fun getSpecIdFromSpecRanksKey(specRankKey: String) =
        specRankKey.substringAfter("_").toInt()
}
