package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoreItem

data class CharacterMythicPlusScoresData(
    val selectedExpansion: Expansion,
    val selectedSeason: Season,
    val scoreItems: List<MythicPlusScoreItem>
)
