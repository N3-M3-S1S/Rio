package com.nemesis.rio.data.ranks.serialization

import com.nemesis.rio.domain.ranks.Ranks
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

object RanksSerialization {

    internal fun parseRanksOrNull(ranksJsonObject: JsonObject): Ranks? {
        fun getRanksValue(key: String) = ranksJsonObject.getValue(key).jsonPrimitive.int
        val worldRank = getRanksValue("world")
        if (worldRank == 0) {
            return null
        }
        val regionRank = getRanksValue("region")
        val realmRank = getRanksValue("realm")
        return Ranks(worldRank, regionRank, realmRank)
    }
}
