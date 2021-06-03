package com.nemesis.rio.domain.mplus.ranks.usecase

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksSource
import com.nemesis.rio.domain.profile.Character

class CharacterHasMythicPlusRanksForCurrentSeason(private val mythicPlusRanksSource: MythicPlusRanksSource) {

    suspend operator fun invoke(character: Character): Boolean =
        mythicPlusRanksSource.characterHasRanksForCurrentSeason(character)
}
