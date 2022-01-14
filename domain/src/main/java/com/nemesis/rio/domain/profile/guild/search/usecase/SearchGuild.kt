package com.nemesis.rio.domain.profile.guild.search.usecase

import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.profile.guild.search.GuildSearchSource
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryRepository
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class SearchGuild(
    private val guildSearchSource: GuildSearchSource,
    private val guildSearchHistory: ProfileSearchHistoryRepository<Guild>
) {
    suspend operator fun invoke(name: String, region: Region, realm: Realm): Guild? =
        guildSearchSource.searchGuild(name, region, realm)?.also {
            guildSearchHistory.add(it)
        }
}
