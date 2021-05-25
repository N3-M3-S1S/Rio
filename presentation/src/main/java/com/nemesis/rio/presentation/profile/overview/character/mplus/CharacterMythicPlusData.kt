package com.nemesis.rio.presentation.profile.overview.character.mplus

import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.CharacterMythicPlusRanksData
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsData
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresData

data class CharacterMythicPlusData(
    val scoresData: CharacterMythicPlusScoresData?,
    val ranksData: CharacterMythicPlusRanksData?,
    val runsData: CharacterMythicPlusRunsData?
)
