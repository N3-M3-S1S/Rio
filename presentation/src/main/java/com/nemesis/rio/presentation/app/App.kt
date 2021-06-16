package com.nemesis.rio.presentation.app

import android.app.Application
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getDefaultComponent
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.nemesis.rio.presentation.BuildConfig
import com.nemesis.rio.presentation.launcher.launcherModule
import com.nemesis.rio.presentation.main.mainActivityModule
import com.nemesis.rio.presentation.mplus.mythicPlusModules
import com.nemesis.rio.presentation.profile.overview.character.characterOverviewModules
import com.nemesis.rio.presentation.profile.overview.guild.guildOverviewModules
import com.nemesis.rio.presentation.profile.profileModule
import com.nemesis.rio.presentation.profile.profileModules
import com.nemesis.rio.presentation.profile.search.history.profileSearchHistoryModules
import com.nemesis.rio.presentation.profile.search.profileSearchModules
import com.nemesis.rio.presentation.raiding.raidingModule
import com.nemesis.rio.presentation.server.realm.realmSelectModule
import com.nemesis.rio.presentation.view.databinding.DefaultBindingAdapters
import kotlinx.coroutines.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import java.io.File

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
        setupEpoxyAsyncFeatures()
        setupDefaultBindingAdapters()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            androidFileProperties()
            modules(
                mainActivityModule,
                launcherModule,
                appModule,
                dataModule,
                profileModule,
                raidingModule,
                realmSelectModule,
            )
            modules(profileModules + profileSearchModules + profileSearchHistoryModules + characterOverviewModules + guildOverviewModules + mythicPlusModules)
        }
    }

    private fun setupEpoxyAsyncFeatures() {
        val handler = EpoxyAsyncUtil.getAsyncBackgroundHandler()
        EpoxyController.defaultDiffingHandler = handler
        EpoxyController.defaultModelBuildingHandler = handler
    }

    private fun setupDefaultBindingAdapters() =
        getKoin().get<DefaultBindingAdapters>().also(DataBindingUtil::setDefaultComponent)

}

val applicationScope = MainScope()

val defaultBindingAdapters =
    getDefaultComponent() as DefaultBindingAdapters
