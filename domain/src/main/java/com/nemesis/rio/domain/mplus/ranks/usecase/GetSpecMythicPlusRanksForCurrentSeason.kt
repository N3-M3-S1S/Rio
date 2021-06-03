package com.nemesis.rio.domain.mplus.ranks.usecase

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksSource
import com.nemesis.rio.domain.mplus.ranks.MythicPlusSpecRanks
import com.nemesis.rio.domain.profile.Character

class GetSpecMythicPlusRanksForCurrentSeason(private val ranksSource: MythicPlusRanksSource) {

    suspend operator fun invoke(
        character: Character,
        scope: MythicPlusRanksScope
    ): MythicPlusSpecRanks =
        ranksSource.getSpecRanksForCurrentSeason(character, scope)
}
