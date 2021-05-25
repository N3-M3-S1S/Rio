package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.presentation.ranks.list.RanksListItem

interface MythicPlusRanksItemsFactory {
    suspend fun getRanksItems(
        character: Character,
        scope: MythicPlusRanksScope
    ): List<RanksListItem>
}
