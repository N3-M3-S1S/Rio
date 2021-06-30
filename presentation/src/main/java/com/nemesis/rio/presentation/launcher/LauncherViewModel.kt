package com.nemesis.rio.presentation.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.data.database.AppDatabaseInitializer
import com.nemesis.rio.data.mplus.scores.colors.update.MythicPlusScoreColorsUpdateScheduler
import com.nemesis.rio.presentation.app.initialization.ApplicationInitializationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LauncherViewModel(
    applicationInitializationState: ApplicationInitializationState,
    private val appDatabaseInitializer: AppDatabaseInitializer,
    private val mythicPlusScoreColorsUpdateScheduler: MythicPlusScoreColorsUpdateScheduler
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
            mythicPlusScoreColorsUpdateScheduler.scheduleMythicPlusScoreColorsUpdate()
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
