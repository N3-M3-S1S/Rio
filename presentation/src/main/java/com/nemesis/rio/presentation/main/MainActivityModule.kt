package com.nemesis.rio.presentation.main

import com.nemesis.rio.domain.profile.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

val navigateToProfileOverviewEventFlowQualifier = qualifier("navigate_to_profile_overview")
val openUrlInBrowserEventFlowQualifier = qualifier("open_url_in_browser")

val mainActivityModule = module {
    viewModel<MainActivityViewModel>()
    single(navigateToProfileOverviewEventFlowQualifier) { MutableSharedFlow<Profile>() } bind Flow::class
    single(openUrlInBrowserEventFlowQualifier) { MutableSharedFlow<String>() } bind Flow::class
}
