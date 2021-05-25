package com.nemesis.rio.data.profile.character.search

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.search.CharacterSearchSource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class CharacterSearchSourceImpl(
    private val characterDatabaseSearchSource: CharacterSearchDatabaseSource,
    private val characterApiSearchSource: CharacterSearchApiSource
) : CharacterSearchSource {

    override suspend fun searchCharacter(name: String, region: Region, realm: Realm): Character? {
        return characterDatabaseSearchSource.searchCharacter(name, region, realm)
            ?: characterApiSearchSource.searchCharacter(name, region, realm)
    }
}
