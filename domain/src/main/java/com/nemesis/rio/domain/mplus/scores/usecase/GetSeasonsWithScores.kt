package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character

class GetSeasonsWithScores(private val mythicPlusScoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character): Map<Expansion, List<Season>> =
        mythicPlusScoresSource.getSeasonsWithScores(character)
}
