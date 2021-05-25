package com.nemesis.rio.domain.profile.character.search

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

interface CharacterSearchSource {
    suspend fun searchCharacter(name: String, region: Region, realm: Realm): Character?
}
