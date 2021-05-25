package com.nemesis.rio.data.raiding.serialization

import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.Raid.*

internal object RaidSerialization {

    fun getRaidJsonValue(raid: Raid) = when (raid) {
        THE_EMERALD_NIGHTMARE -> "the-emerald-nightmare"
        TRIAL_OF_VALOR -> "trial-of-valor"
        THE_NIGHTHOLD -> "the-nighthold"
        TOMB_OF_SARGERAS -> "tomb-of-sargeras"
        ANTORUS_THE_BURNING_THRONE -> "antorus-the-burning-throne"

        ULDIR -> "uldir"
        BATTLE_OF_DAZARALOR -> "battle-of-dazaralor"
        CRUCIBLE_OF_STORMS -> "crucible-of-storms"
        THE_ETERNAL_PALACE -> "the-eternal-palace"
        NYALOTHA_THE_WAKING_CITY -> "nyalotha-the-waking-city"

        CASTLE_NATHRIA -> "castle-nathria"
    }

     fun parseRaidByJsonValueOrNull(raidJsonValue: String) =
        enumValues<Raid>().find { raid -> getRaidJsonValue(raid) == raidJsonValue }
}
