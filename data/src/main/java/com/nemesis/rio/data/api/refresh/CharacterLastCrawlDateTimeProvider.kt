package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.search.characterNotFoundInAPI
import com.nemesis.rio.domain.profile.Character
import kotlinx.datetime.Instant

class CharacterLastCrawlDateTimeProvider(private val rioApiClient: RioApiClient) :
    ProfileLastCrawlDateTimeProvider<Character> {

    override suspend fun getProfileLastCrawlInstant(profile: Character): Instant =
        with(profile) { rioApiClient.getCharacterLastCrawlDateTime(name, region, realm) }
            ?: characterNotFoundInAPI()
}
