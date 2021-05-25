package com.nemesis.rio.domain.profile.guild.search

import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

interface GuildSearchSource {
    suspend fun searchGuild(name: String, region: Region, realm: Realm): Guild?
}
