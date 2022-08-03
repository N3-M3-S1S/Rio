package com.nemesis.rio.domain.mplus

import java.time.Duration

enum class Dungeon(val completionTimer: Duration) {
    GRIMRAIL_DEPOT(Duration.ofMinutes(30)),
    IRON_DOCKS(Duration.ofMinutes(32)),
    MECHAGON_JUNKYARD(Duration.ofMinutes(38)),
    MECHAGON_WORKSHOP(Duration.ofMinutes(32)),
    KARAZHAN_LOWER(Duration.ofMinutes(42)),
    KARAZHAN_UPPER(Duration.ofMinutes(35)),
    TAZAVESH_GMBT(Duration.ofMinutes(30)),
    TAZAVESH_STRT(Duration.ofMinutes(39)),
}
