package com.nemesis.rio.presentation.profile.overview.guild.ranks

import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.factory
import org.koin.dsl.module

val guildRaidRanksModule = module {
    scope<GuildOverviewParentFragment> {
        viewModel<GuildRaidRanksViewModel>()
    }
    factory<GuildRaidRanksListDataFactory>()
    factory<GuildRaidRanksListDataController>()
}
