package com.nemesis.rio.presentation.profile.search.history

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.usecase.RemoveProfileFromSearchHistory
import com.nemesis.rio.domain.profile.search.usecase.AddProfileToSearchHistory
import com.nemesis.rio.presentation.app.applicationScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileSearchHistoryItemActionsHandler<P : Profile>(
    private val navigateToProfileOverviewEventFlow: MutableSharedFlow<Profile>,
    private val removeProfileFromSearchHistory: RemoveProfileFromSearchHistory<P>,
    private val addProfileToSearchHistory: AddProfileToSearchHistory<P>
) {

    fun onClicked(profile: P) {
        applicationScope.launch {
            addProfileToSearchHistory(profile)
            navigateToProfileOverviewEventFlow.emit(profile)
        }
    }

    fun onRemoved(profile: P) {
        applicationScope.launch { removeProfileFromSearchHistory(profile) }
    }
}
