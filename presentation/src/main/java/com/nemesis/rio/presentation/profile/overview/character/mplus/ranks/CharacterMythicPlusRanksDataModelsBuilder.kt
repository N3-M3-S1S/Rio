package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import com.airbnb.epoxy.EpoxyController
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemDropdown
import com.nemesis.rio.presentation.itemNoContentForCurrentSeasonMessage
import com.nemesis.rio.presentation.itemTextHeader
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.mplus.ranks.stringResID
import com.nemesis.rio.presentation.profile.stringResId
import com.nemesis.rio.presentation.ranks.list.ranks
import com.nemesis.rio.presentation.view.epoxy.EpoxyModelsBuilderDelegate
import splitties.resources.appStr

class CharacterMythicPlusRanksDataModelsBuilder(
    private val ranksDataActionsHandler: CharacterMythicPlusRanksDataActionsHandler
) :
    EpoxyModelsBuilderDelegate<CharacterMythicPlusRanksData?> {

    override fun buildModels(data: CharacterMythicPlusRanksData?, controller: EpoxyController) {
        with(controller) {
            ranksHeader()
            if (data != null) {
                with(data) {
                    ranksTypeDropdown(selectedRanksType)
                    ranksScopeDropdown(selectedRanksScope, faction)
                    ranks(ranksListData)
                }
            } else {
                noRanksForCurrentSeasonMessage()
            }
        }
    }

    private fun EpoxyController.ranksHeader() {
        itemTextHeader {
            id("ranks_header")
            textResId(R.string.character_mplus_ranks_header)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    private fun EpoxyController.ranksTypeDropdown(selectedRanksType: MythicPlusRanksType) {
        itemDropdown {
            id("ranks_type")
            titleResId(R.string.character_mplus_ranks_type_select_title)
            text(appStr(selectedRanksType.stringResID))
            onClick(ranksDataActionsHandler::onRanksTypeClicked)
        }
    }

    private fun EpoxyController.ranksScopeDropdown(
        selectedRanksScope: MythicPlusRanksScope?,
        faction: Faction
    ) {
        val textResId = when (selectedRanksScope) {
            MythicPlusRanksScope.GLOBAL -> R.string.ranks_span_global
            MythicPlusRanksScope.FACTION -> faction.stringResId
            null -> R.string.ranks_scope_all
        }

        itemDropdown {
            id("ranks_scope")
            titleResId(R.string.character_mplus_ranks_scope_select_title)
            textResId(textResId)
            onClick(ranksDataActionsHandler::onRanksScopeClicked)
        }
    }

    private fun EpoxyController.noRanksForCurrentSeasonMessage() {
        itemNoContentForCurrentSeasonMessage {
            id("no_ranks")
            textResId(R.string.character_mplus_no_ranks_for_current_season)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }
}
