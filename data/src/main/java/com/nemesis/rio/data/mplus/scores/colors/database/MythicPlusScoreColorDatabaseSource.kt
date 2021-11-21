package com.nemesis.rio.data.mplus.scores.colors.database

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.domain.mplus.scores.color.MythicPlusScoreColorSource
import com.nemesis.rio.domain.mplus.seasons.Season

class MythicPlusScoreColorDatabaseSource(private val mythicPlusScoreColorDao: MythicPlusScoreColorDao) :
    MythicPlusScoreColorSource {

    override suspend fun getHexColorForMythicPlusScore(
        score: MythicPlusScore,
        season: Season
    ): HexColor? = mythicPlusScoreColorDao.getHexColorForMythicPlusScore(score, season)
}
