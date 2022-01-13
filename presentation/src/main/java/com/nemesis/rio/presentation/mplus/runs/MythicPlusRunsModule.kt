package com.nemesis.rio.presentation.mplus.runs

import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.mplus.runs.database.MythicPlusRunsDatabaseSource
import com.nemesis.rio.data.mplus.runs.database.MythicPlusRunsSaver
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.runs.MythicPlusRunsSource
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.mplus.runs.sorting.strategy.*
import com.nemesis.rio.domain.mplus.runs.usecase.GetMythicPlusRunsForCurrentSeason
import com.nemesis.rio.domain.mplus.runs.usecase.SortMythicPlusRuns
import com.nemesis.rio.presentation.mplus.nameResId
import com.nemesis.rio.presentation.profile.character.characterQualifier
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.qualifier
import org.koin.dsl.factory
import org.koin.dsl.module
import splitties.resources.str

val mythicPlusRunsModule = module {
    val sortStrategiesQualifier = qualifier("sortStrategies")
    factory<MythicPlusRunsSaver>()
    factory<GetMythicPlusRunsForCurrentSeason>()
    single { get<AppDatabase>().mythicPlusRunsDao }

    factory<MythicPlusRunsSource> {
        MythicPlusRunsDatabaseSource(runsDao = get(), profileIDProvider = get(characterQualifier))
    }
    factory { SortMythicPlusRuns(get(sortStrategiesQualifier)) }
    factory(sortStrategiesQualifier) {
        val dungeonToLocalizedName =
            Dungeon.values().associate { it to androidApplication().str(it.nameResId) }

        val sortStrategies = enumValues<MythicPlusRunsSortingOption>().associate {
            val sortStrategy = when (it) {
                MythicPlusRunsSortingOption.BY_DUNGEON_NAME -> SortRunsByDungeonName(
                    dungeonToLocalizedName
                )
                MythicPlusRunsSortingOption.BY_KEYSTONE_LEVEL -> SortRunsByKeystoneLevel
                MythicPlusRunsSortingOption.BY_COMPLETE_DATE -> SortByCompletedDateTime
                MythicPlusRunsSortingOption.BY_KEYSTONE_UPGRADES -> SortRunsByKeystoneUpgrades
                MythicPlusRunsSortingOption.BY_SCORE -> SortRunsByScore
            }
            it to sortStrategy
        }
        sortStrategies
    }
}
