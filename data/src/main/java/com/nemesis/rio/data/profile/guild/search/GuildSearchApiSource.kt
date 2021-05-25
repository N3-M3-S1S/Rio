package com.nemesis.rio.data.profile.guild.search

import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.search.database.GuildSearchResponseSaver
import com.nemesis.rio.domain.profile.guild.search.GuildSearchSource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class GuildSearchApiSource(
    private val rioApiClient: RioApiClient,
    private val guildSearchResponseSaver: GuildSearchResponseSaver
) : GuildSearchSource {

    override suspend fun searchGuild(name: String, region: Region, realm: Realm) =
        rioApiClient.searchGuild(name, region, realm)
            ?.also { guildSearchResponse ->
                guildSearchResponseSaver
                    .saveOrUpdateResponseContent(guildSearchResponse)
            }
            ?.guild
}
