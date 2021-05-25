package com.nemesis.rio.presentation.profile.overview.guild.ranks

import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragment
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val guildRaidRanksModule = module {
    scope<GuildOverviewParentFragment> {
        viewModel<GuildRaidRanksViewModel>()
    }
    factory<GuildRaidRanksListDataFactory>()
    factory<GuildRaidRanksListDataController>()
}
