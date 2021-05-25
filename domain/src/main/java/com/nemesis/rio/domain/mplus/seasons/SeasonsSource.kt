package com.nemesis.rio.domain.mplus.seasons

import com.nemesis.rio.domain.game.Expansion

interface SeasonsSource {
    suspend fun getSeasons(expansion: Expansion): List<Season>
    suspend fun getCurrentSeason(): Season
}
