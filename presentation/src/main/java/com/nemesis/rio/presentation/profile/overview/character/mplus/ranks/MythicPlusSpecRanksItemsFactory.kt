package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.usecase.GetSpecMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.presentation.profile.character.attributes.iconResId
import com.nemesis.rio.presentation.profile.character.attributes.stringResId
import com.nemesis.rio.presentation.ranks.list.RanksListItem
import splitties.resources.appStr

class MythicPlusSpecRanksItemsFactory(
    private val getSpecMythicPlusRanksForCurrentSeason: GetSpecMythicPlusRanksForCurrentSeason
) :
    MythicPlusRanksItemsFactory {

    override suspend fun getRanksItems(
        character: Character,
        scope: MythicPlusRanksScope
    ): List<RanksListItem> =
        getSpecMythicPlusRanksForCurrentSeason(character, scope).map { (spec, ranks) ->
            RanksListItem(appStr(spec.stringResId), ranks, spec.iconResId)
        }
}
