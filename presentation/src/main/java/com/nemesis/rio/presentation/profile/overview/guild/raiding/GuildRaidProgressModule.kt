package com.nemesis.rio.presentation.profile.overview.guild.raiding

import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragment
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val guildRaidProgressModule = module {
    scope<GuildOverviewParentFragment> {
        viewModel<GuildRaidProgressViewModel>()
    }
    factory<GuildRaidProgressListDataFactory>()
}
