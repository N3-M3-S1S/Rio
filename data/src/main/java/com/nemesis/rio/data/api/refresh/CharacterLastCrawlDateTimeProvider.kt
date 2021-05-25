package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.search.characterNotFoundInAPI
import com.nemesis.rio.domain.profile.character.Character
import kotlinx.datetime.LocalDateTime

class CharacterLastCrawlDateTimeProvider(private val rioApiClient: RioApiClient) :
    ProfileLastCrawlDateTimeProvider<Character> {

    override suspend fun getProfileLastCrawlDateTime(profile: Character): LocalDateTime =
        with(profile) { rioApiClient.getCharacterLastCrawlDateTime(name, region, realm) }
            ?: characterNotFoundInAPI()
}
