package com.nemesis.rio.presentation.profile.search.history

import com.nemesis.rio.presentation.profile.search.character.history.characterSearchHistoryModule
import com.nemesis.rio.presentation.profile.search.character.history.list.CharacterSearchHistoryListItemsSource
import com.nemesis.rio.presentation.profile.search.guild.history.guildSearchHistoryModule
import com.nemesis.rio.presentation.profile.search.guild.history.list.GuildSearchHistoryListItemsSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val profileSearchHistoryModule = module {
    factory<ProfileSearchHistoryController>()

    viewModel {
        val profileSearchHistoryItemsSource: ProfileSearchHistoryListItemsSource = get()

        ProfileSearchHistoryViewModel(
            profileSearchHistoryItemsSource = profileSearchHistoryItemsSource,
            loadingStateController = get()
        )
    }

    factory<ProfileSearchHistoryListItemsSource> {
        val characterSearchHistoryListItemsSource =
            get<CharacterSearchHistoryListItemsSource>()

        val guildSearchHistoryListItemsSource =
            get<GuildSearchHistoryListItemsSource>()

        CombinedProfileSearchHistoryListItemsSource(
            listOf(
                characterSearchHistoryListItemsSource,
                guildSearchHistoryListItemsSource
            )
        )
    }
}

val profileSearchHistoryModules =
    listOf(profileSearchHistoryModule, characterSearchHistoryModule, guildSearchHistoryModule)
