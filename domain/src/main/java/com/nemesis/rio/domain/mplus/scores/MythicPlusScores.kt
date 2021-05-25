package com.nemesis.rio.domain.mplus.scores

import com.nemesis.rio.domain.profile.character.attributes.Role

typealias MythicPlusScore = Float

data class MythicPlusScores(
    val overallScore: MythicPlusScore,
    val roleScores: Map<Role, MythicPlusScore>,
)
