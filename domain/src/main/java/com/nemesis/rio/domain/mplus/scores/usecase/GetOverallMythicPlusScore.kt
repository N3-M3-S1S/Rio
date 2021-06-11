package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character

class GetOverallMythicPlusScore(private val mythicPlusScoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character, season: Season): MythicPlusScore =
        mythicPlusScoresSource.getOverallScore(character, season)
}
