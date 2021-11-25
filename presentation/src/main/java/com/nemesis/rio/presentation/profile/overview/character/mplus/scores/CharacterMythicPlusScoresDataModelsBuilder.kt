package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.group
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemDropdown
import com.nemesis.rio.presentation.itemMplusScore
import com.nemesis.rio.presentation.itemTextHeader
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoresType
import com.nemesis.rio.presentation.mplus.scores.stringResId
import com.nemesis.rio.presentation.view.epoxy.EpoxyModelsBuilderDelegate

class CharacterMythicPlusScoresDataModelsBuilder(
    private val scoresDataActionsHandler: CharacterMythicPlusScoresDataActionsHandler
) :
    EpoxyModelsBuilderDelegate<CharacterMythicPlusScoresData?> {

    override fun buildModels(data: CharacterMythicPlusScoresData?, controller: EpoxyController) {
        with(controller) {
            if (data != null) {
                scoresHeader()
                with(data) {
                    scoresTypeDropdown(selectedScoresType)
                    seasonDropdown(selectedSeason)
                    scores(scoreItems)
                }
            }
        }
    }

    private fun EpoxyController.scoresHeader() {
        itemTextHeader {
            id("scores_header")
            textResId(R.string.character_mplus_scores_header)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    private fun EpoxyController.scoresTypeDropdown(selectedScoresType: MythicPlusScoresType) {
        itemDropdown {
            id("scores_type")
            titleResId(R.string.character_mplus_scores_type)
            textResId(selectedScoresType.stringResId)
            onClick(scoresDataActionsHandler::onSelectScoresTypeClicked)
        }
    }

    private fun EpoxyController.seasonDropdown(selectedSeason: Season) {
        itemDropdown {
            id("scores_season")
            titleResId(R.string.character_mplus_season_select_title)
            text(selectedSeason)
            onClick(scoresDataActionsHandler::onSelectSeasonClicked)
        }
    }

    private fun EpoxyController.scores(scoreItems: List<CharacterMythicPlusScoreItem>) {
        group(R.layout.item_mplus_scores_group) {
            id("scores_group")
            scoreItems.forEach { (title, score, scoreColor) ->
                itemMplusScore {
                    id(title)
                    title(title)
                    score(score)
                    scoreColor(scoreColor)
                }
            }
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }
}
