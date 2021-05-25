package com.nemesis.rio.domain.mplus.seasons.usecase

import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.mplus.seasons.SeasonsSource

class GetCurrentSeason(private val seasonsSource: SeasonsSource) {

    suspend operator fun invoke(): Season = seasonsSource.getCurrentSeason()
}
