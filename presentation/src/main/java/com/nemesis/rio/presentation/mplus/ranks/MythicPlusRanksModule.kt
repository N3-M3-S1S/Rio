package com.nemesis.rio.presentation.mplus.ranks

import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.mplus.ranks.database.MythicPlusRanksDatabaseSource
import com.nemesis.rio.data.mplus.ranks.database.MythicPlusRanksSaver
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksSource
import com.nemesis.rio.domain.mplus.ranks.usecase.CharacterHasMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.mplus.ranks.usecase.GetClassMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.mplus.ranks.usecase.GetOverallMythicPlusRanksForCurrentSeason
import com.nemesis.rio.domain.mplus.ranks.usecase.GetSpecMythicPlusRanksForCurrentSeason
import com.nemesis.rio.presentation.profile.character.characterQualifier
import org.koin.dsl.factory
import org.koin.dsl.module

val mythicPlusRanksModule = module {
    factory<MythicPlusRanksSaver>()
    factory<GetClassMythicPlusRanksForCurrentSeason>()
    factory<GetSpecMythicPlusRanksForCurrentSeason>()
    factory<GetOverallMythicPlusRanksForCurrentSeason>()
    factory<CharacterHasMythicPlusRanksForCurrentSeason>()
    single { get<AppDatabase>().mythicPlusRanksDao }
    factory<MythicPlusRanksSource> {
        MythicPlusRanksDatabaseSource(ranksDao = get(), profileIDProvider = get(characterQualifier))
    }
}
