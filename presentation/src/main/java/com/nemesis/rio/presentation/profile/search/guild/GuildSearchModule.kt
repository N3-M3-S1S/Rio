package com.nemesis.rio.presentation.profile.search.guild

import com.nemesis.rio.data.api.search.database.GuildSearchResponseSaver
import com.nemesis.rio.data.profile.database.ProfileSaver
import com.nemesis.rio.data.profile.guild.api.GuildSearchFields
import com.nemesis.rio.data.profile.guild.search.GuildSearchApiSource
import com.nemesis.rio.data.profile.guild.search.GuildSearchDatabaseSource
import com.nemesis.rio.data.profile.guild.search.GuildSearchSourceImpl
import com.nemesis.rio.data.raiding.progress.database.RaidProgressSaver
import com.nemesis.rio.data.raiding.ranks.database.GuildRaidRanksSaver
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.profile.guild.search.GuildSearchSource
import com.nemesis.rio.domain.profile.guild.search.usecase.SearchGuild
import com.nemesis.rio.presentation.profile.guild.guildQualifier
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy

val guildSearchModule = module {
    factoryBy<GuildSearchSource, GuildSearchSourceImpl>()
    factory<GuildSearchApiSource>()
    factory<GuildSearchFields.Factory>()
    factory { GuildSearchDatabaseSource(guildDao = get(guildQualifier)) }
    factory { SearchGuild(guildSearchSource = get(), guildSearchHistory = get(guildQualifier)) }

    factory {
        val guildSaver = get<ProfileSaver<Guild>>(guildQualifier)
        val raidProgressSaver = get<RaidProgressSaver>(guildQualifier)
        val raidRanksSaver = get<GuildRaidRanksSaver>()
        GuildSearchResponseSaver(guildSaver, raidRanksSaver, raidProgressSaver)
    }
}
