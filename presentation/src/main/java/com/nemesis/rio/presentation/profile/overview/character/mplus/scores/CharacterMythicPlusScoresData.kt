package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.nemesis.rio.domain.mplus.seasons.Season

data class CharacterMythicPlusScoresData(
    val selectedSeason: Season,
    val scoreItems: List<CharacterMythicPlusScoreItem>
)
