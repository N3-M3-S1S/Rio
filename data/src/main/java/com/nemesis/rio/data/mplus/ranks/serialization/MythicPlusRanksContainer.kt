package com.nemesis.rio.data.mplus.ranks.serialization

import com.nemesis.rio.domain.mplus.ranks.MythicPlusClassRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusOverallRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusSpecRanks

data class MythicPlusRanksContainer(
    val classRanks: MythicPlusClassRanks,
    val specRanks: MythicPlusSpecRanks,
    val overallRanks: MythicPlusOverallRanks,
)
