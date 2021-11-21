package com.nemesis.rio.presentation.profile.overview.guild.ranks

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.game.stringResId
import com.nemesis.rio.presentation.itemCenterText
import com.nemesis.rio.presentation.itemTextHeader
import com.nemesis.rio.presentation.ranks.list.ranks

class GuildRaidRanksListDataController : TypedEpoxyController<GuildRaidRanksListData>() {

    override fun buildModels(data: GuildRaidRanksListData?) {
        if (!data.isNullOrEmpty()) {
            raidRanksList(data)
        } else {
            noRaidRanksMessage()
        }
    }

    private fun EpoxyController.noRaidRanksMessage() {
        itemCenterText {
            id(-1)
            textResId(R.string.guild_raid_ranks_not_found)
        }
    }

    private fun EpoxyController.raidRanksList(data: GuildRaidRanksListData) {
        data.forEach { (expansion, ranksListData) ->
            itemTextHeader {
                id(expansion.hashCode())
                textResId(expansion.stringResId)
            }
            ranks(ranksListData)
        }
    }

//    private fun EpoxyController.expansionTitle(expansion: Expansion) {
//        itemTextHeader {
//            id(expansion.hashCode())
//            textResId(expansion.stringResId)
//        }
//    }
}
