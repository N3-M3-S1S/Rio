package com.nemesis.rio.domain.mplus.scores

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character

interface MythicPlusScoresSource {
    suspend fun getScoresForSeason(character: Character, season: Season): MythicPlusScores
    suspend fun getExpansionsWithScores(character: Character): List<Expansion>
    suspend fun getSeasonsWithScores(character: Character, expansion: Expansion): List<Season>
}
