package com.nemesis.rio.data.mplus.scores.serialization

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.profile.character.attributes.Role

data class MythicPlusScoresContainer(
    val seasonApiValue: String,
    val overallScore: MythicPlusScore,
    val roleScores: Map<Role, MythicPlusScore>
)
