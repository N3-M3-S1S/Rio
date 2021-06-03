package com.nemesis.rio.data.profile.character.update

import com.nemesis.rio.data.api.search.characterNotFoundInAPI
import com.nemesis.rio.data.profile.character.search.CharacterSearchApiSource
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.update.usecase.ProfileUpdateStrategy

class CharacterUpdateStrategy(private val characterApiSearchSource: CharacterSearchApiSource) :
    ProfileUpdateStrategy<Character> {

    override suspend fun update(profile: Character): Character =
        with(profile) { characterApiSearchSource.searchCharacter(name, region, realm) }
            ?: characterNotFoundInAPI()
}
