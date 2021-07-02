package com.nemesis.rio.domain.game

import com.nemesis.rio.domain.raiding.Raid

enum class Expansion {
    SHADOWLANDS, BFA, LEGION;

    val raids
        get() = when (this) {
            SHADOWLANDS -> listOf(Raid.SANCTUM_OF_DOMINATION, Raid.CASTLE_NATHRIA)

            BFA -> listOf(
                Raid.NYALOTHA_THE_WAKING_CITY,
                Raid.THE_ETERNAL_PALACE,
                Raid.CRUCIBLE_OF_STORMS,
                Raid.BATTLE_OF_DAZARALOR,
                Raid.ULDIR,
            )

            LEGION -> listOf(
                Raid.ANTORUS_THE_BURNING_THRONE,
                Raid.TOMB_OF_SARGERAS,
                Raid.THE_NIGHTHOLD,
                Raid.TRIAL_OF_VALOR,
                Raid.THE_EMERALD_NIGHTMARE,
            )
        }
}
