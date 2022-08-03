package com.nemesis.rio.data.mplus.runs.serialization

import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.Dungeon.*

internal object DungeonSerialization {
    private val Dungeon.shortName: String
        get() = when (this) {
            GRIMRAIL_DEPOT -> "GD"
            IRON_DOCKS -> "ID"
            MECHAGON_JUNKYARD -> "YARD"
            MECHAGON_WORKSHOP -> "WORK"
            KARAZHAN_LOWER -> "LOWR"
            KARAZHAN_UPPER -> "UPPR"
            TAZAVESH_GMBT -> "GMBT"
            TAZAVESH_STRT -> "STRT"
        }

    fun parseDungeonByShorNameOrNull(dungeonShortName: String) =
        enumValues<Dungeon>().find { dungeon -> dungeon.shortName == dungeonShortName }
}
