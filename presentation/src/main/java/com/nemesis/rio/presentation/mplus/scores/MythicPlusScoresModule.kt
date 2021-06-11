package com.nemesis.rio.presentation.mplus.scores

import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoresDatabaseSource
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoresSaver
import com.nemesis.rio.data.mplus.seasons.database.SeasonsDatabaseSource
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.scores.usecase.GetExpansionsWithScores
import com.nemesis.rio.domain.mplus.scores.usecase.GetOverallMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetRoleMythicPlusScores
import com.nemesis.rio.domain.mplus.scores.usecase.GetSeasonsWithScoresForExpansion
import com.nemesis.rio.domain.mplus.seasons.SeasonsSource
import com.nemesis.rio.domain.mplus.seasons.usecase.GetCurrentSeason
import com.nemesis.rio.presentation.profile.character.characterQualifier
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy

val mythicPlusScoresModule = module {
    factory<GetOverallMythicPlusScore>()
    factory<GetRoleMythicPlusScores>()
    factory<GetExpansionsWithScores>()
    factory<GetSeasonsWithScoresForExpansion>()
    factoryBy<SeasonsSource, SeasonsDatabaseSource>()
    factory<MythicPlusScoresSaver>()
    factory<GetCurrentSeason>()
    single { get<AppDatabase>().mythicPlusScoresDao }
    single { get<AppDatabase>().seasonsDao }
    factory<MythicPlusScoresSource> {
        MythicPlusScoresDatabaseSource(
            scoresDao = get(),
            profileIDProvider = get(characterQualifier)
        )
    }
}
