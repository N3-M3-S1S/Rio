package com.nemesis.rio.presentation.profile.search.guild.history

import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.presentation.profile.guild.guildQualifier
import com.nemesis.rio.presentation.profile.search.guild.history.list.GuildSearchHistoryListItemsSource
import com.nemesis.rio.presentation.profile.search.history.profileSearchHistoryCoreDependencies
import org.koin.dsl.module

val guildSearchHistoryModule = module {
    profileSearchHistoryCoreDependencies<Guild>()

    factory {
        GuildSearchHistoryListItemsSource(
            getProfileSearchHistory = get(guildQualifier),
            guildSearchHistoryActionsHandler = get(guildQualifier)
        )
    }
}
