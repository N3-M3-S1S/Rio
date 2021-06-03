package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.usecase.GetOverallMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.character.attributes.iconResId
import com.nemesis.rio.presentation.profile.character.attributes.nameResId
import com.nemesis.rio.presentation.ranks.list.RanksListItem
import splitties.resources.appStr

class MythicPlusOverallRanksItemsFactory(
    private val getOverallMythicPlusRanksForCurrentSeason: GetOverallMythicPlusRanksForCurrentSeason
) :
    MythicPlusRanksItemsFactory {

    override suspend fun getRanksItems(
        character: Character,
        scope: MythicPlusRanksScope
    ): List<RanksListItem> {
        val overallRanks = getOverallMythicPlusRanksForCurrentSeason(character, scope)

        val overallRanksItems = mutableListOf<RanksListItem>()

        val overallRanksItem = RanksListItem(
            appStr(R.string.character_mplus_ranks_all_classes_and_roles),
            overallRanks.overallRanks,
            R.drawable.role_overall
        )
        overallRanksItems.add(overallRanksItem)

        overallRanks.overallRoleRanks.mapTo(overallRanksItems) { (role, ranks) ->
            RanksListItem(
                appStr(role.nameResId),
                ranks,
                role.iconResId
            )
        }

        return overallRanksItems
    }
}
