package com.nemesis.rio.presentation.profile.overview.character.mplus.runs

import android.content.res.Configuration
import com.airbnb.epoxy.EpoxyController
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.sorting.SortingOrder
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemDropdown
import com.nemesis.rio.presentation.itemNoContentForCurrentSeasonMessage
import com.nemesis.rio.presentation.itemTextHeader
import com.nemesis.rio.presentation.mplus.runs.mythicPlusRunItem
import com.nemesis.rio.presentation.mplus.runs.stringResId
import com.nemesis.rio.presentation.sorting.stringResId
import com.nemesis.rio.presentation.view.epoxy.EpoxyModelsBuilderDelegate
import splitties.init.appCtx

class CharacterMythicPlusRunsDataModelsBuilder(
    private val runsDataActionsHandler: CharacterMythicPlusRunsDataActionsHandler
) :
    EpoxyModelsBuilderDelegate<CharacterMythicPlusRunsData?> {

    override fun buildModels(data: CharacterMythicPlusRunsData?, controller: EpoxyController) {
        with(controller) {
            runsHeader()
            if (data != null) {
                with(data) {
                    sortingOptionDropdown(selectedSortingOption)
                    sortingOrderDropdown(selectedSortingOrder)
                    runs(runs)
                }
            } else {
                noRunsForCurrentSeasonMessage()
            }
        }
    }

    private fun EpoxyController.runsHeader() {
        itemTextHeader {
            id("runs_header")
            textResId(R.string.character_mplus_runs_header)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    private fun EpoxyController.sortingOptionDropdown(selectedSortingOption: MythicPlusRunsSortingOption) {
        itemDropdown {
            id("runs_sorting_option")
            titleResId(R.string.sort_by_title)
            textResId(selectedSortingOption.stringResId)
            onClick(runsDataActionsHandler::onRunsSortingOptionClicked)
        }
    }

    private fun EpoxyController.sortingOrderDropdown(selectedSortingOrder: SortingOrder) {
        itemDropdown {
            id("runs_sorting_order")
            titleResId(R.string.sort_order_title)
            textResId(selectedSortingOrder.stringResId)
            onClick(runsDataActionsHandler::onRunsSortingOrderClicked)
        }
    }

    private fun EpoxyController.runs(runs: List<MythicPlusRun>) {
        runs.forEach { run ->
            mythicPlusRunItem {
                id(run.completedAt.hashCode())
                run(run)
                onOpenRunInBrowserClicked(runsDataActionsHandler::onOpenRunInBrowserClicked)
                spanSizeOverride { totalSpanCount, _, _ -> getSpanSizeForRun(totalSpanCount) }
            }
        }
    }

    private fun EpoxyController.noRunsForCurrentSeasonMessage() {
        itemNoContentForCurrentSeasonMessage {
            id("no_runs_data")
            textResId(R.string.character_mplus_no_runs_for_current_season)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    private fun getSpanSizeForRun(totalSpanCount: Int): Int =
        if (appCtx.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            totalSpanCount
        } else {
            1
        }
}
