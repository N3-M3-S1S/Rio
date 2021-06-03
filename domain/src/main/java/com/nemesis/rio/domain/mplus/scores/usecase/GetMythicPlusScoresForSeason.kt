package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character

class GetMythicPlusScoresForSeason(private val scoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character, season: Season) =
        scoresSource.getScoresForSeason(character, season)
}
