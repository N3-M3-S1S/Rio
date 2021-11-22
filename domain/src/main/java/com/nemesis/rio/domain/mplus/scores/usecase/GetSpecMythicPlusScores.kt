package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Spec

class GetSpecMythicPlusScores(private val mythicPlusScoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character, season: Season): Map<Spec, MythicPlusScore> =
        mythicPlusScoresSource.getSpecScores(character, season)

}