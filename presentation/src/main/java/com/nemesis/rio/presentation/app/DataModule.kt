package com.nemesis.rio.presentation.app

import android.content.Context
import androidx.core.content.getSystemService
import androidx.work.WorkManager
import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.retrofit.RioApi
import com.nemesis.rio.data.connection.NetworkConnectionStatus
import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.database.AppDatabaseInitializer
import com.nemesis.rio.data.database.asset.*
import com.nemesis.rio.data.mplus.scores.colors.database.MythicPlusScoreColorsAssetUpdater
import com.nemesis.rio.data.mplus.seasons.database.SeasonsAssetUpdater
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.experimental.builder.single

val dataModule = module {
    single<RioApiClient>()
    single<AssetDatabaseChangesInspector>()
    single<AssetDatabaseChecksumStorage>()
    single { AppDatabase.build(androidContext()) }
    single { AssetDatabaseFileManager(get(), androidApplication().externalCacheDir!!.path) }
    single { androidApplication().assets }
    single { androidApplication().getSharedPreferences("shared_preferences", Context.MODE_PRIVATE) }
    single {
        val seasonAssetUpdater = get<SeasonsAssetUpdater>()
        val scoreColorsAssetUpdater = get<MythicPlusScoreColorsAssetUpdater>()
        AppDatabaseInitializer(
            get(),
            get(),
            listOf(seasonAssetUpdater, scoreColorsAssetUpdater)
        )
    }
    single { RioApi.create() }
    single { NetworkConnectionStatus(androidApplication().getSystemService()!!) }
    single { WorkManager.getInstance(androidApplication()) }
}
