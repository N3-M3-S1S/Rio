package com.nemesis.rio.domain.mplus

import java.time.Duration

enum class Dungeon(val duration: Duration) {
    DE_OTHER_SIDE(Duration.ofMinutes(43)),
    MISTS_OF_THIRNA_SCITHE(Duration.ofMinutes(30)),
    SANGUINE_DEPTHS(Duration.ofMinutes(41)),
    HALLS_OF_ATONEMENT(Duration.ofMinutes(31)),
    NECROTIC_WAKE(Duration.ofMinutes(36)),
    SPIRES_OF_ASCENSION(Duration.ofMinutes(39)),
    THEATER_OF_PAIN(Duration.ofMinutes(37)),
    PLAGUEFALL(Duration.ofMinutes(38))
}
