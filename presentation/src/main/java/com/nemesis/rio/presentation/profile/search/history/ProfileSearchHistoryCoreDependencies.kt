package com.nemesis.rio.presentation.profile.search.history

import com.nemesis.rio.data.profile.database.ProfileSearchHistorySourceImpl
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistorySource
import com.nemesis.rio.domain.profile.search.usecase.GetProfileSearchHistory
import com.nemesis.rio.domain.profile.search.usecase.RemoveProfileFromSearchHistory
import com.nemesis.rio.domain.profile.search.usecase.UpdateProfileLastDateTimeSearch
import com.nemesis.rio.presentation.main.navigateToProfileOverviewEventFlowQualifier
import com.nemesis.rio.presentation.profile.profileQualifier
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.module.Module

inline fun <reified P : Profile> Module.profileSearchHistoryCoreDependencies() {
    val profileQualifier = profileQualifier<P>()

    factory<ProfileSearchHistorySource<P>>(profileQualifier) {
        ProfileSearchHistorySourceImpl(
            profileDao = get(profileQualifier),
            profileIDProvider = get(profileQualifier)
        )
    }

    factory(profileQualifier) {
        GetProfileSearchHistory<P>(profileSearchHistorySource = get(profileQualifier))
    }

    factory(profileQualifier) {
        RemoveProfileFromSearchHistory<P>(profileSearchHistorySource = get(profileQualifier))
    }

    factory(profileQualifier) {
        UpdateProfileLastDateTimeSearch<P>(profileSearchHistorySource = get(profileQualifier))
    }

    factory(profileQualifier) {
        val profileOverviewNavigator: MutableSharedFlow<Profile> =
            get(navigateToProfileOverviewEventFlowQualifier)

        val removeProfileFromSearchHistory: RemoveProfileFromSearchHistory<P> =
            get(profileQualifier)

        val updateProfileLastDateTimeSearch: UpdateProfileLastDateTimeSearch<P> =
            get(profileQualifier)

        ProfileSearchHistoryItemActionsHandler(
            profileOverviewNavigator,
            removeProfileFromSearchHistory,
            updateProfileLastDateTimeSearch
        )
    }
}
