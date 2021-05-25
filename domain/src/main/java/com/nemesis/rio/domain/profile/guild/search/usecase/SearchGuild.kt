package com.nemesis.rio.domain.profile.guild.search.usecase

import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.profile.guild.search.GuildSearchSource
import com.nemesis.rio.domain.profile.search.ProfileSearchHistorySource
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class SearchGuild(
    private val guildSearchSource: GuildSearchSource,
    private val guildSearchHistory: ProfileSearchHistorySource<Guild>
) {
    suspend operator fun invoke(name: String, region: Region, realm: Realm) =
        guildSearchSource.searchGuild(name, region, realm)?.also {
            guildSearchHistory.addOrUpdate(it)
        }
}
