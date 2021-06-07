package com.nemesis.rio.domain.mplus.scores.color.usecase

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.domain.mplus.scores.color.MythicPlusScoreColorSource
import com.nemesis.rio.domain.mplus.seasons.Season

class GetHexColorForMythicPlusScore(private val mythicPlusScoreColorSource: MythicPlusScoreColorSource) {
    private val defaultHexColor = "#ffffff"

    suspend operator fun invoke(score: MythicPlusScore, season: Season): HexColor =
        mythicPlusScoreColorSource.getHexColorForMythicPlusScore(score, season) ?: defaultHexColor

}
