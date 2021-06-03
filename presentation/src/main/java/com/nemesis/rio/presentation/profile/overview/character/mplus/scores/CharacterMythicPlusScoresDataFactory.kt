package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.usecase.GetMythicPlusScoresForSeason
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.presentation.mplus.scores.toScoreItems

class CharacterMythicPlusScoresDataFactory(private val getMythicPlusScoresForSeason: GetMythicPlusScoresForSeason) {

    suspend fun getScoresData(
        character: Character,
        selectedSeason: Season,
        selectedExpansion: Expansion
    ): CharacterMythicPlusScoresData {
        val scoreListItemsForSelectedSeasons =
            getMythicPlusScoresForSeason(character, selectedSeason).toScoreItems()
        return CharacterMythicPlusScoresData(
            selectedExpansion,
            selectedSeason,
            scoreListItemsForSelectedSeasons
        )
    }
}
