package com.nemesis.rio.domain.raiding

import com.nemesis.rio.domain.game.Expansion

enum class Raid(val bossesCount: Int) {

    // Shadowlands
    SEPULCHER_OF_THE_FIRST_ONES(11),
    SANCTUM_OF_DOMINATION(10),
    CASTLE_NATHRIA(10),

    // Battle for Azeroth
    NYALOTHA_THE_WAKING_CITY(12),
    THE_ETERNAL_PALACE(8),
    CRUCIBLE_OF_STORMS(2),
    BATTLE_OF_DAZARALOR(9),
    ULDIR(8),

    // Legion
    ANTORUS_THE_BURNING_THRONE(11),
    TOMB_OF_SARGERAS(9),
    THE_NIGHTHOLD(10),
    TRIAL_OF_VALOR(3),
    THE_EMERALD_NIGHTMARE(7);

    val expansion
        get() = enumValues<Expansion>().first { it.raids.contains(this) }
}
