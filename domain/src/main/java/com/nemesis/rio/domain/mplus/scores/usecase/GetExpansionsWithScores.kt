package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.profile.character.Character

class GetExpansionsWithScores(private val mythicPlusScoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character): List<Expansion> =
        mythicPlusScoresSource.getExpansionsWithScores(character)
}
