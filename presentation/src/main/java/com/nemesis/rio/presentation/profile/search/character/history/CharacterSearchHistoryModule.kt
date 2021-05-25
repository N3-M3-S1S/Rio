package com.nemesis.rio.presentation.profile.search.character.history

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.presentation.profile.character.characterQualifier
import com.nemesis.rio.presentation.profile.search.character.history.list.CharacterSearchHistoryListItemsSource
import com.nemesis.rio.presentation.profile.search.history.profileSearchHistoryCoreDependencies
import org.koin.dsl.module

val characterSearchHistoryModule = module {
    profileSearchHistoryCoreDependencies<Character>()

    factory {
        CharacterSearchHistoryListItemsSource(
            getProfileSearchHistory = get(characterQualifier),
            characterSearchHistoryActionsHandler = get(characterQualifier)
        )
    }
}
