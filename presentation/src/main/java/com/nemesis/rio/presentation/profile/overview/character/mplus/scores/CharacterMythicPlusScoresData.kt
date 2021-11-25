package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoresType

data class CharacterMythicPlusScoresData(
    val selectedScoresType: MythicPlusScoresType,
    val selectedSeason: Season,
    val scoreItems: List<CharacterMythicPlusScoreItem>
)
