package com.nemesis.rio.presentation.raiding

import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.raiding.achievements.database.RaidAchievementsSaver
import com.nemesis.rio.data.raiding.database.CharacterRaidingDatabaseSource
import com.nemesis.rio.data.raiding.database.GuildRaidingDatabaseSource
import com.nemesis.rio.data.raiding.progress.database.RaidProgressEntity
import com.nemesis.rio.data.raiding.progress.database.RaidProgressSaver
import com.nemesis.rio.data.raiding.ranks.database.GuildRaidRanksSaver
import com.nemesis.rio.domain.raiding.achievements.usecase.GetAchievementsForRaid
import com.nemesis.rio.domain.raiding.achievements.usecase.GetAllRaidAchievements
import com.nemesis.rio.domain.raiding.progress.usecase.GetAllRaidProgress
import com.nemesis.rio.domain.raiding.progress.usecase.GetProgressForRaid
import com.nemesis.rio.domain.raiding.ranks.usecase.GetAllRaidRanks
import com.nemesis.rio.domain.raiding.ranks.usecase.GetRanksForRaid
import com.nemesis.rio.domain.raiding.source.CharacterRaidingSource
import com.nemesis.rio.domain.raiding.source.GuildRaidingSource
import com.nemesis.rio.domain.raiding.usecase.GetCurrentRaid
import com.nemesis.rio.presentation.profile.character.characterQualifier
import com.nemesis.rio.presentation.profile.guild.guildQualifier
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressListDataController
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val raidingModule = module {
    factory<GetProgressForRaid>()
    factory<GetAchievementsForRaid>()
    factory<GetAllRaidAchievements>()
    factory<GetRanksForRaid>()
    factory<GetAllRaidRanks>()
    factory<GuildRaidRanksSaver>()
    factory<RaidAchievementsSaver>()
    factory<GetAllRaidProgress>()
    factory<RaidProgressListDataController>()
    factory<GetCurrentRaid>()
    single { get<AppDatabase>().raidAchievementsDao }
    single { get<AppDatabase>().raidProgressDao }
    single { get<AppDatabase>().raidRanksDao }
    factory(characterQualifier) {
        RaidProgressSaver(
            get(),
            RaidProgressEntity.PROFILE_TYPE_CHARACTER
        )
    }
    factory(guildQualifier) { RaidProgressSaver(get(), RaidProgressEntity.PROFILE_TYPE_GUILD) }
    single<CharacterRaidingSource> {
        CharacterRaidingDatabaseSource(
            raidProgressDao = get(),
            raidAchievementsDao = get(),
            profileIDProvider = get(characterQualifier)
        )
    }

    single<GuildRaidingSource> {
        GuildRaidingDatabaseSource(
            raidProgressDao = get(),
            raidRanksDao = get(),
            profileIDProvider = get(guildQualifier)
        )
    }
}
