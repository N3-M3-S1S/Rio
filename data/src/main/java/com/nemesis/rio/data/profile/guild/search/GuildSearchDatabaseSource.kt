package com.nemesis.rio.data.profile.guild.search

import com.nemesis.rio.data.profile.guild.database.GuildDao
import com.nemesis.rio.domain.profile.guild.search.GuildSearchSource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class GuildSearchDatabaseSource(private val guildDao: GuildDao) : GuildSearchSource {
    override suspend fun searchGuild(name: String, region: Region, realm: Realm) =
        guildDao.searchGuild(name, region, realm)
}
