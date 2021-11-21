package com.nemesis.rio.presentation.mplus.scores

import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.mplus.scores.colors.update.MythicPlusScoreColorsUpdateScheduler
import com.nemesis.rio.data.mplus.scores.colors.update.MythicPlusScoreColorsUpdateWorker
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
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.bind
import org.koin.dsl.factory
import org.koin.dsl.module

val mythicPlusScoresModule = module {
    factory<GetOverallMythicPlusScore>()
    factory<GetRoleMythicPlusScores>()
    factory<GetExpansionsWithScores>()
    factory<GetSeasonsWithScoresForExpansion>()
    factory<MythicPlusScoresSaver>()
    factory<MythicPlusScoreColorsAssetUpdater>()
    factory<MythicPlusScoreColorDatabaseSource>() bind MythicPlusScoreColorSource::class
    factory<GetHexColorForMythicPlusScore>()
    factory<GetCurrentSeason>()
    factory<SeasonsDatabaseSource>() bind SeasonsSource::class
    factory<SeasonsAssetUpdater>()
    factory<MythicPlusScoreColorsUpdateScheduler>()
    worker { MythicPlusScoreColorsUpdateWorker(get(), get(), get(), androidApplication(), it.get()) }
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
