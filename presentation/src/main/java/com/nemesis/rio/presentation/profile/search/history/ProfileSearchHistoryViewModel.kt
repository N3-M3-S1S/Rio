package com.nemesis.rio.presentation.profile.search.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.LoadingStateDelegate
import com.nemesis.rio.presentation.utils.extensions.toLiveData
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class ProfileSearchHistoryViewModel(
    profileSearchHistoryItemsSource: ProfileSearchHistoryListItemsSource,
    loadingStateController: LoadingStateController,
) : ViewModel(), LoadingStateDelegate by loadingStateController {

    val searchHistory = profileSearchHistoryItemsSource
        .getProfileSearchHistoryItemsFlow()
        .onStart {
            loadingStateController.setLoadingDelayed(true, viewModelScope)
        }
        .onEach {
            loadingStateController.setLoadingDelayed(false, viewModelScope)
        }
        .toLiveData(viewModelScope)
}
