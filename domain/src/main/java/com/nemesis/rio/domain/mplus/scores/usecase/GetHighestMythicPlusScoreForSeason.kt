package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import kotlin.math.max

class GetHighestMythicPlusScoreForSeason(private val mythicPlusScoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character, season: Season): MythicPlusScore {
        val scoresForSeason = mythicPlusScoresSource.getScoresForSeason(character, season)
        return with(scoresForSeason) {
            val bestRoleScore = roleScores.values.maxOrNull() ?: 0f
            max(overallScore, bestRoleScore)
        }
    }
}
