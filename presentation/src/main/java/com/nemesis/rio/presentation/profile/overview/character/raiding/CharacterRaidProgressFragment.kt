package com.nemesis.rio.presentation.profile.overview.character.raiding

import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressFragment
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CharacterRaidProgressFragment : RaidProgressFragment<CharacterRaidProgressViewModel>() {

    override fun getViewModel(): RaidProgressViewModel =
        requireParentFragment().getViewModel<CharacterRaidProgressViewModel>()

}
