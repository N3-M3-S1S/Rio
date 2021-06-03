package com.nemesis.rio.domain.mplus.ranks

import com.nemesis.rio.domain.profile.Character

interface MythicPlusRanksSource {
    suspend fun getSpecRanksForCurrentSeason(
        character: Character,
        scope: MythicPlusRanksScope
    ): MythicPlusSpecRanks

    suspend fun getClassRanksForCurrentSeason(
        character: Character,
        scope: MythicPlusRanksScope
    ): MythicPlusClassRanks

    suspend fun getOverallRanksForCurrentSeason(
        character: Character,
        scope: MythicPlusRanksScope
    ): MythicPlusOverallRanks

    suspend fun characterHasRanksForCurrentSeason(character: Character): Boolean
}
