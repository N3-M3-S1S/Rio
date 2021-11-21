package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor

data class CharacterMythicPlusScoreItem(
    val title: String,
    val score: MythicPlusScore,
    val scoreColor: HexColor
)
