package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.airbnb.epoxy.Typed2EpoxyController
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.presentation.ItemOptionSelectStickyHeaderBindingModel_
import com.nemesis.rio.presentation.game.stringResId
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.itemOptionSelectStickyHeader

class CharacterMythicPlusScoresSeasonSelectController(private val onSeasonSelected: (Season) -> Unit) :
    Typed2EpoxyController<Map<Expansion, List<Season>>, Season>() {

    override fun buildModels(
        expansionsWithSeasons: Map<Expansion, List<Season>>,
        selectedSeason: Season
    ) {
        expansionsWithSeasons.forEach { (expansion, seasons) ->
            itemOptionSelectStickyHeader {
                id(expansion.hashCode())
                textResId(expansion.stringResId)
            }
            seasons.forEach { season ->
                itemOption {
                    id(season.hashCode())
                    isSelected(season == selectedSeason)
                    string(season)
                    onClick(this@CharacterMythicPlusScoresSeasonSelectController.onSeasonSelected)
                }
            }
        }
    }

    override fun isStickyHeader(position: Int): Boolean =
        adapter.getModelAtPosition(position) is ItemOptionSelectStickyHeaderBindingModel_


}