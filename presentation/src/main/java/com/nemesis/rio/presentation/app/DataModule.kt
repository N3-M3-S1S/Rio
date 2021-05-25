package com.nemesis.rio.presentation.app

import androidx.core.content.getSystemService
import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.api.retrofit.RioApi
import com.nemesis.rio.data.connection.NetworkConnectionStatus
import com.nemesis.rio.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.experimental.builder.single

val dataModule = module {
    single<RioApiClient>()
    single { AppDatabase.build(androidContext()) }
    single { RioApi.create() }
    single { NetworkConnectionStatus(androidApplication().getSystemService()!!) }
}
