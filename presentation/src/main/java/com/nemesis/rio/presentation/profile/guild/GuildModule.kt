package com.nemesis.rio.presentation.profile.guild

import com.nemesis.rio.data.api.refresh.GuildLastCrawlDateTimeProvider
import com.nemesis.rio.data.api.refresh.ProfileLastCrawlDateTimeProvider
import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.profile.database.ProfileDao
import com.nemesis.rio.data.profile.guild.refresh.GuildUpdateStrategy
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.profile.update.usecase.ProfileUpdateStrategy
import com.nemesis.rio.presentation.profile.profileCoreDependencies
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy

val guildQualifier = qualifier<Guild>()

val guildModule = module {
    profileCoreDependencies<Guild>()
    single(guildQualifier) { get<AppDatabase>().guildDao } bind ProfileDao::class
    factoryBy<ProfileUpdateStrategy<Guild>, GuildUpdateStrategy>(guildQualifier)
    factoryBy<ProfileLastCrawlDateTimeProvider<Guild>, GuildLastCrawlDateTimeProvider>(guildQualifier)
}
