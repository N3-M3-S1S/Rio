package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.search.guildNotFoundInAPI
import com.nemesis.rio.domain.profile.Guild
import kotlinx.datetime.Instant

class GuildLastCrawlDateTimeProvider(private val rioApiClient: RioApiClient) :
    ProfileLastCrawlDateTimeProvider<Guild> {

    override suspend fun getProfileLastCrawlInstant(profile: Guild): Instant =
        with(profile) { rioApiClient.getGuildLastCrawlDateTime(name, region, realm) }
            ?: guildNotFoundInAPI()
}
