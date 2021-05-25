package com.nemesis.rio.presentation.ranks.list

import com.airbnb.epoxy.EpoxyController
import com.nemesis.rio.presentation.itemRanks
import com.nemesis.rio.presentation.itemRanksHeader

fun EpoxyController.ranks(ranksListData: RanksListData) {
    ranksListData.forEach { (ranksHeader, ranksItems) ->
        itemRanksHeader {
            id(ranksHeader)
            title(ranksHeader)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }

        ranksItems.forEach { ranksItem ->
            itemRanks {
                id(ranksHeader, ranksItem.title)
                ranksItem(ranksItem)
                spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
            }
        }
    }
}
