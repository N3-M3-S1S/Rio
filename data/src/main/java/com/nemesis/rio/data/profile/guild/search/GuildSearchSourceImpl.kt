package com.nemesis.rio.data.profile.guild.search

import com.nemesis.rio.domain.profile.guild.search.GuildSearchSource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class GuildSearchSourceImpl(
    private val guildDatabaseSearchSource: GuildSearchDatabaseSource,
    private val guildApiSearchSource: GuildSearchApiSource
) : GuildSearchSource {

    override suspend fun searchGuild(name: String, region: Region, realm: Realm) =
        guildDatabaseSearchSource.searchGuild(name, region, realm)
            ?: guildApiSearchSource.searchGuild(name, region, realm)
}
