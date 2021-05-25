package com.nemesis.rio.domain.mplus.ranks.usecase

import com.nemesis.rio.domain.mplus.ranks.MythicPlusClassRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksSource
import com.nemesis.rio.domain.profile.character.Character

class GetClassMythicPlusRanksForCurrentSeason(private val ranksSource: MythicPlusRanksSource) {

    suspend operator fun invoke(
        character: Character,
        scope: MythicPlusRanksScope
    ): MythicPlusClassRanks =
        ranksSource.getClassRanksForCurrentSeason(character, scope)
}
