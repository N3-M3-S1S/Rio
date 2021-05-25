package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class CharacterMythicPlusScoresSeasonSelectDialogFragment : BaseOptionSelectDialogFragment() {
    private val seasonsKey = "seasons"

    companion object {
        const val REQUEST_KEY = "select_season"
        const val SELECTED_SEASON_KEY = "selected_season"

        fun create(seasons: List<Season>, selectedSeason: Season) =
            CharacterMythicPlusScoresSeasonSelectDialogFragment().apply {
                arguments = bundleOf(seasonsKey to seasons, SELECTED_SEASON_KEY to selectedSeason)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.optionSelectToolbar.setTitle(R.string.mplus_seasons)
        setupSeasonsList()
    }

    private fun setupSeasonsList() {
        viewBinding.optionSelectRecyclerview.withModels {
            val selectedSeason = requireArguments().getString(SELECTED_SEASON_KEY)!!
            @Suppress("UNCHECKED_CAST") val seasons =
                requireArguments().get(seasonsKey) as List<Season>

            seasons.forEach { season ->
                itemOption {
                    id(season)
                    string(season)
                    isSelected(season == selectedSeason)
                    onClick(::onSeasonSelected)
                }
            }
        }
    }

    private fun onSeasonSelected(season: Season) {
        parentFragmentManager
            .setFragmentResult(REQUEST_KEY, bundleOf(SELECTED_SEASON_KEY to season))
        dismiss()
    }
}
