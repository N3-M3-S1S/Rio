package com.nemesis.rio.presentation.profile.search.history

import com.nemesis.rio.data.profile.database.ProfileSearchHistoryDatabaseRepository
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryRepository
import com.nemesis.rio.domain.profile.search.usecase.AddProfileToSearchHistory
import com.nemesis.rio.domain.profile.search.usecase.GetProfileSearchHistory
import com.nemesis.rio.domain.profile.search.usecase.RemoveProfileFromSearchHistory
import com.nemesis.rio.presentation.main.navigateToProfileOverviewEventFlowQualifier
import com.nemesis.rio.presentation.profile.profileQualifier
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.module.Module

inline fun <reified P : Profile> Module.profileSearchHistoryCoreDependencies() {
    val profileQualifier = profileQualifier<P>()

    factory<ProfileSearchHistoryRepository<P>>(profileQualifier) {
        ProfileSearchHistoryDatabaseRepository(
            profileDao = get(profileQualifier),
            profileIDProvider = get(profileQualifier)
        )
    }

    factory(profileQualifier) {
        GetProfileSearchHistory<P>(profileSearchHistoryRepository = get(profileQualifier))
    }

    factory(profileQualifier) {
        RemoveProfileFromSearchHistory<P>(profileSearchHistoryRepository = get(profileQualifier))
    }

    factory(profileQualifier) {
        AddProfileToSearchHistory<P>(profileSearchHistoryRepository = get(profileQualifier))
    }

    factory(profileQualifier) {
        val profileOverviewNavigator: MutableSharedFlow<Profile> =
            get(navigateToProfileOverviewEventFlowQualifier)

        val removeProfileFromSearchHistory: RemoveProfileFromSearchHistory<P> =
            get(profileQualifier)

        val addProfileToSearchHistory: AddProfileToSearchHistory<P> =
            get(profileQualifier)

        ProfileSearchHistoryItemActionsHandler(
            profileOverviewNavigator,
            removeProfileFromSearchHistory,
            addProfileToSearchHistory
        )
    }
}
