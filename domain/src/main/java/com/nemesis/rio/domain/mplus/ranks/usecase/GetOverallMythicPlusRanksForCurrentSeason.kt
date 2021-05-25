package com.nemesis.rio.domain.mplus.ranks.usecase

import com.nemesis.rio.domain.mplus.ranks.MythicPlusOverallRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksSource
import com.nemesis.rio.domain.profile.character.Character

class GetOverallMythicPlusRanksForCurrentSeason(private val ranksSource: MythicPlusRanksSource) {

    suspend operator fun invoke(
        character: Character,
        scope: MythicPlusRanksScope
    ): MythicPlusOverallRanks =
        ranksSource.getOverallRanksForCurrentSeason(character, scope)
}
