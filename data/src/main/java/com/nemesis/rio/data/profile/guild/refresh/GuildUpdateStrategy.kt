package com.nemesis.rio.data.profile.guild.refresh

import com.nemesis.rio.data.profile.guild.search.GuildSearchApiSource
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.profile.update.usecase.ProfileUpdateStrategy

class GuildUpdateStrategy(private val guildApiSearchSource: GuildSearchApiSource) :
    ProfileUpdateStrategy<Guild> {

    override suspend fun update(profile: Guild): Guild = with(profile) {
        guildApiSearchSource.searchGuild(name, region, realm)
    } ?: error("Guild not found in API")
}
