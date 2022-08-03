package com.nemesis.rio.presentation.profile.overview.guild.raiding

import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressFragment
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class GuildRaidProgressFragment : RaidProgressFragment<GuildRaidProgressViewModel>() {

    override fun getViewModel(): RaidProgressViewModel =
        requireParentFragment().getViewModel<GuildRaidProgressViewModel>()

}
