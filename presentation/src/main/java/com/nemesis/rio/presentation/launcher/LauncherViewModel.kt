package com.nemesis.rio.presentation.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.data.database.AppDatabaseInitializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import com.nemesis.rio.presentation.app.initialization.ApplicationInitializationState

class LauncherViewModel(
    applicationInitializationState: ApplicationInitializationState,
    private val appDatabaseInitializer: AppDatabaseInitializer
) :
    ViewModel() {

    private val _applicationInitializing = MutableLiveData(false)
    val applicationInitializing: LiveData<Boolean> = _applicationInitializing

    private val _navigateToMainScreenEvent = MutableSharedFlow<Unit>(replay = 1)
    val navigateToMainScreenEvent: Flow<Unit> = _navigateToMainScreenEvent

    init {
        viewModelScope.launch {
            if (applicationInitializationState.applicationNeedsInitialization()) {
                _applicationInitializing.value = true
                initializeApplication()
                applicationInitializationState.setApplicationInitialized()
                _applicationInitializing.value = false
            }
            sendNavigateToMainScreenEvent()
        }
    }

    private suspend fun initializeApplication() {
        appDatabaseInitializer.initializeAppDatabase()
    }

    private fun sendNavigateToMainScreenEvent() {
        _navigateToMainScreenEvent.tryEmit(Unit)
    }

}
