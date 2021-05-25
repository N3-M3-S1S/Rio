package com.nemesis.rio.data.profile.character.search

import com.nemesis.rio.data.profile.character.database.CharacterDao
import com.nemesis.rio.domain.profile.character.search.CharacterSearchSource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class CharacterSearchDatabaseSource(private val characterDao: CharacterDao) :
    CharacterSearchSource {

    override suspend fun searchCharacter(name: String, region: Region, realm: Realm) =
        characterDao.searchCharacter(name, region, realm)
}
