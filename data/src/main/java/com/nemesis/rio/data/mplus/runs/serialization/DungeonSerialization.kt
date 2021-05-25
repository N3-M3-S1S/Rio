package com.nemesis.rio.data.mplus.runs.serialization

import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.Dungeon.*

internal object DungeonSerialization {
    private val Dungeon.jsonValue: String
        get() = when (this) {
            DE_OTHER_SIDE -> "DOS"
            MISTS_OF_THIRNA_SCITHE -> "MISTS"
            SANGUINE_DEPTHS -> "SD"
            HALLS_OF_ATONEMENT -> "HOA"
            NECROTIC_WAKE -> "NW"
            SPIRES_OF_ASCENSION -> "SOA"
            THEATER_OF_PAIN -> "TOP"
            PLAGUEFALL -> "PF"
        }

    fun parseDungeonByJsonValueOrNull(dungeonJsonValue: String) =
        enumValues<Dungeon>().find { dungeon -> dungeon.jsonValue == dungeonJsonValue }
}
