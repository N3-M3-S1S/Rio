package com.nemesis.rio.domain.mplus.scores.color

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.seasons.Season

interface MythicPlusScoreColorSource {
    suspend fun getHexColorForMythicPlusScore(score: MythicPlusScore, season: Season): HexColor?
}
