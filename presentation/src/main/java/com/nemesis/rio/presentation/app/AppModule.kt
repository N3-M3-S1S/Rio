package com.nemesis.rio.presentation.app

import com.nemesis.rio.presentation.app.browser.UrlBrowser
import com.nemesis.rio.presentation.app.clipboard.Clipboard
import com.nemesis.rio.presentation.app.initialization.ApplicationInitializationState
import com.nemesis.rio.presentation.app.messages.MessageManager
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.view.databinding.DefaultBindingAdapters
import com.nemesis.rio.utils.checksum.FileChecksumGenerator
import com.nemesis.rio.utils.checksum.MD5FileChecksumGenerator
import org.koin.dsl.bind
import org.koin.dsl.factory
import org.koin.dsl.module
import org.koin.dsl.single
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val appModule = module {
    single<MessageManager>()
    single<Clipboard>()
    single<UrlBrowser>()
    single<ApplicationInitializationState>()
    factory<LoadingStateController>()
    single<MD5FileChecksumGenerator>() bind FileChecksumGenerator::class
    single { DefaultBindingAdapters().apply { setDateTimeFormatter(get()) } }
    single {
        DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault())
    }
}
