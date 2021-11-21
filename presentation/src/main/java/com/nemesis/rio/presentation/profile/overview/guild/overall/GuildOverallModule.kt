package com.nemesis.rio.presentation.profile.overview.guild.overall

import com.nemesis.rio.presentation.profile.guild.guildQualifier
import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val guildOverallModule = module {
    scope<GuildOverviewParentFragment> {
        viewModel<GuildOverallViewModel>()
    }
    factory {
        GuildOverallDataFactory(
            getCurrentRaid = get(),
            getProgressForRaid = get(),
            getRanksForRaid = get(),
            getProfileLastRefreshDateTime = get(guildQualifier)
        )
    }
}
