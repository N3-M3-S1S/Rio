package com.nemesis.rio.presentation.profile.search

import com.nemesis.rio.presentation.main.navigateToProfileOverviewEventFlowQualifier
import com.nemesis.rio.presentation.profile.search.character.characterSearchModule
import com.nemesis.rio.presentation.profile.search.guild.guildSearchModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.factory
import org.koin.dsl.module

val profileSearchModule = module {
    factory<ProfileSearchPreferences>()

    viewModel {
        ProfileSearchViewModel(
            searchPreferences = get(),
            searchCharacter = get(),
            searchGuild = get(),
            getRealmListForRegion = get(),
            navigateToProfileOverviewScreenEventFlow = get(
                navigateToProfileOverviewEventFlowQualifier
            ),
            messageManager = get()
        )
    }
}

val profileSearchModules =
    listOf(
        profileSearchModule,
        guildSearchModule,
        characterSearchModule
    )
