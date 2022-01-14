package com.nemesis.rio.domain.profile.character.search.usecase

import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.search.CharacterSearchSource
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryRepository
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class SearchCharacter(
    private val characterSearchSource: CharacterSearchSource,
    private val characterSearchHistory: ProfileSearchHistoryRepository<Character>
) {
    suspend operator fun invoke(name: String, region: Region, realm: Realm): Character? =
        characterSearchSource.searchCharacter(name, region, realm)?.also {
            characterSearchHistory.add(it)
        }
}
