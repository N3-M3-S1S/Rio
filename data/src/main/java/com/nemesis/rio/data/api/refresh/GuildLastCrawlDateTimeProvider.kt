package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.search.guildNotFoundInAPI
import com.nemesis.rio.domain.profile.Guild
import kotlinx.datetime.LocalDateTime

class GuildLastCrawlDateTimeProvider(private val rioApiClient: RioApiClient) :
    ProfileLastCrawlDateTimeProvider<Guild> {

    override suspend fun getProfileLastCrawlDateTime(profile: Guild): LocalDateTime =
        with(profile) { rioApiClient.getGuildLastCrawledDateTime(name, region, realm) }
            ?: guildNotFoundInAPI()
}
