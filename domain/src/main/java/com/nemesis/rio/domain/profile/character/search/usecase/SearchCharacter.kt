package com.nemesis.rio.domain.profile.character.search.usecase

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.search.CharacterSearchSource
import com.nemesis.rio.domain.profile.search.ProfileSearchHistorySource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class SearchCharacter(
    private val characterSearchSource: CharacterSearchSource,
    private val characterSearchHistory: ProfileSearchHistorySource<Character>
) {
    suspend operator fun invoke(name: String, region: Region, realm: Realm) =
        characterSearchSource.searchCharacter(name, region, realm)?.also {
            characterSearchHistory.addOrUpdate(it)
        }
}
