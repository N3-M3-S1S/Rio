package com.nemesis.rio.data.profile.character.search

import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.search.database.CharacterSearchResponseSaver
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.search.CharacterSearchSource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class CharacterSearchApiSource(
    private val rioApiClient: RioApiClient,
    private val characterSearchResponseSaver: CharacterSearchResponseSaver
) : CharacterSearchSource {

    override suspend fun searchCharacter(
        name: String,
        region: Region,
        realm: Realm
    ): Character? = rioApiClient.searchCharacter(name, region, realm)
        ?.also { characterSearchResponse ->
            characterSearchResponseSaver.saveOrUpdateResponseContent(
                characterSearchResponse
            )
        }
        ?.character
}
