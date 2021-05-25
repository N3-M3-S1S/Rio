package com.nemesis.rio.data.mplus.seasons.database

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.mplus.seasons.SeasonsSource

class SeasonsDatabaseSource(private val seasonsDao: SeasonsDao) : SeasonsSource {

    override suspend fun getSeasons(expansion: Expansion): List<Season> =
        seasonsDao.getSeasonsForExpansion(expansion)

    override suspend fun getCurrentSeason(): Season = seasonsDao.getLastSeason()
}
