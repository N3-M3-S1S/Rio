package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.ranks.list.RanksListData

data class CharacterMythicPlusRanksData(
    val faction: Faction,
    val selectedRanksType: MythicPlusRanksType,
    val selectedRanksScope: MythicPlusRanksScope?,
    val ranksListData: RanksListData
)
