package com.nemesis.rio.presentation.profile.overview

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.presentation.main.openUrlInBrowserEventFlowQualifier
import com.nemesis.rio.presentation.profile.profileQualifier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.ScopeDSL
import org.koin.dsl.bind

inline fun <reified P : Profile> ScopeDSL.profileOverviewCoreDependencies() {
    val profileQualifier = profileQualifier<P>()
    viewModel { (profile: P) ->
        ProfileOverviewViewModel(
            profile = profile,
            profileSharedFlow = get(),
            updateProfile = get(profileQualifier),
            isProfileUpdated = get(profileQualifier),
            openUrlEventFlow = get(openUrlInBrowserEventFlowQualifier),
            messageManager = get()
        )
    }
    scoped { MutableSharedFlow<P>(replay = 1) } bind Flow::class
}
