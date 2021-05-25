package com.nemesis.rio.data.mplus.ranks.database

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.ranks.Ranks

abstract class BaseClassOverallMythicPlusRanksEntity(
    val ranksType: Int,
    ranks: Ranks,
    scope: MythicPlusRanksScope,
    characterID: Long
) : BaseMythicPlusRanksEntity(ranks, scope, characterID) {
    companion object {
        const val RANKS_TYPE_CLASS = 0
        const val RANKS_TYPE_OVERALL = 1
    }
}
