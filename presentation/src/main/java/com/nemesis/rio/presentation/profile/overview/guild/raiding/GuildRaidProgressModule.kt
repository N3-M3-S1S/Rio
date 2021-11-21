package com.nemesis.rio.presentation.profile.overview.guild.raiding

import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.factory
import org.koin.dsl.module

val guildRaidProgressModule = module {
    scope<GuildOverviewParentFragment> {
        viewModel<GuildRaidProgressViewModel>()
    }
    factory<GuildRaidProgressListDataFactory>()
}
