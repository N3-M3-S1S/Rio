package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CharacterMythicPlusScoresSeasonSelectDialogFragment : BaseOptionSelectDialogFragment() {
    private val expansionsWithSeasonsKey = "expansionsWithSeasons"
    private val seasonSelectController: CharacterMythicPlusScoresSeasonSelectController by inject {
        parametersOf(
            ::onSeasonSelected
        )
    }

    companion object {
        const val REQUEST_KEY = "select_season"
        const val SELECTED_SEASON_KEY = "selected_season"

        fun create(expansionsWithSeasons: Map<Expansion, List<Season>>, selectedSeason: Season) =
            CharacterMythicPlusScoresSeasonSelectDialogFragment().apply {
                arguments = bundleOf(
                    expansionsWithSeasonsKey to expansionsWithSeasons,
                    SELECTED_SEASON_KEY to selectedSeason
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.optionSelectToolbar.setTitle(R.string.mplus_seasons)
        setupSeasonsList()
    }

    private fun setupSeasonsList() {
        val selectedSeason = requireArguments().getString(SELECTED_SEASON_KEY)!!
        @Suppress("UNCHECKED_CAST")
        val expansionsWithSeasons =
            requireArguments().get(expansionsWithSeasonsKey) as Map<Expansion, List<Season>>

        viewBinding.optionSelectRecyclerview.setController(seasonSelectController)
        seasonSelectController.setData(expansionsWithSeasons, selectedSeason)
    }

    private fun onSeasonSelected(season: Season) {
        parentFragmentManager
            .setFragmentResult(REQUEST_KEY, bundleOf(SELECTED_SEASON_KEY to season))
        dismiss()
    }
}
