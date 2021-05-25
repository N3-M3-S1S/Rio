package com.nemesis.rio.presentation.profile.overview.raiding

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.game.stringResId
import com.nemesis.rio.presentation.itemCenterText
import com.nemesis.rio.presentation.itemTextHeader

class RaidProgressListDataController : TypedEpoxyController<RaidProgressListData>() {

    override fun buildModels(data: RaidProgressListData) {
        if (data.isEmpty()) {
            buildRaidProgressNotFoundModel()
        } else {
            buildRaidProgressListModels(data)
        }
    }

    private fun buildRaidProgressNotFoundModel() {
        itemCenterText {
            id(-1)
            textResId(R.string.raid_progress_not_found)
        }
    }

    private fun buildRaidProgressListModels(data: RaidProgressListData) {
        data.forEach { (expansion, listItems) ->
            buildExpansionTitle(expansion)
            listItems.forEach { listItem ->
                    raidProgressItemModelView {
                        id(listItem.raid.hashCode())
                        raidProgressListItem(listItem)
                }
            }
        }
    }

    private fun buildExpansionTitle(expansion: Expansion) {
        itemTextHeader {
            id(expansion.hashCode())
            textResId(expansion.stringResId)
            onBind { _, view, _ ->
                val staggeredGridLayoutParams =
                    view.dataBinding.root.layoutParams as? StaggeredGridLayoutManager.LayoutParams
                staggeredGridLayoutParams?.isFullSpan = true
            }
        }
    }
}
