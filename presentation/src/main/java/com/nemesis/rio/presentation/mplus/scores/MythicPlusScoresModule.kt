package com.nemesis.rio.presentation.mplus.scores

import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.mplus.scores.colors.database.MythicPlusScoreColorDatabaseSource
import com.nemesis.rio.data.mplus.scores.colors.database.MythicPlusScoreColorsAssetUpdater
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoresDatabaseSource
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoresSaver
import com.nemesis.rio.data.mplus.seasons.database.SeasonsAssetUpdater
import com.nemesis.rio.data.mplus.seasons.database.SeasonsDatabaseSource
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.scores.color.MythicPlusScoreColorSource
import com.nemesis.rio.domain.mplus.scores.color.usecase.GetHexColorForMythicPlusScore
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
    factory<MythicPlusScoresSaver>()
    factory<MythicPlusScoreColorsAssetUpdater>()
    factoryBy<MythicPlusScoreColorSource, MythicPlusScoreColorDatabaseSource>()
    factory<GetHexColorForMythicPlusScore>()
    factory<GetCurrentSeason>()
    factoryBy<SeasonsSource, SeasonsDatabaseSource>()
    factory<SeasonsAssetUpdater>()
    single { get<AppDatabase>().mythicPlusScoresDao }
    single { get<AppDatabase>().seasonsDao }
    single { get<AppDatabase>().mythicPlusScoreColorDao }
    factory<MythicPlusScoresSource> {
        MythicPlusScoresDatabaseSource(
            scoresDao = get(),
            profileIDProvider = get(characterQualifier)
        )
    }
}
