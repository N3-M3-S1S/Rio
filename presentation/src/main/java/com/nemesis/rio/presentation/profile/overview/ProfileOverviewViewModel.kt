package com.nemesis.rio.presentation.profile.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.data.connection.NotConnectedToNetworkException
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.update.usecase.CheckIsProfileUpdated
import com.nemesis.rio.domain.profile.update.usecase.UpdateProfile
import com.nemesis.rio.presentation.app.messages.MessageManager
import com.nemesis.rio.presentation.app.messages.logAndSendExceptionMessage
import com.nemesis.rio.presentation.app.messages.profileAlreadyUpdatedMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileOverviewViewModel<P : Profile>(
    private var profile: P,
    private val profileSharedFlow: MutableSharedFlow<P>,
    private val updateProfile: UpdateProfile<P>,
    private val isProfileUpdated: CheckIsProfileUpdated<P>,
    private val openUrlEventFlow: MutableSharedFlow<String>,
    private val messageManager: MessageManager
) : ViewModel() {

    private val _isProfileUpdating = MutableLiveData<Boolean>()
    val isProfileUpdating: LiveData<Boolean> = _isProfileUpdating

    private val profileUpdateErrorHandler = CoroutineExceptionHandler { context, throwable ->
        _isProfileUpdating.value = false
        val isLaunchedInInit = context[CoroutineName]?.name == profileUpdateInitCoroutineName
        val showExceptionIfLaunchedInInit = throwable !is NotConnectedToNetworkException
        if (!isLaunchedInInit || showExceptionIfLaunchedInInit) {
            messageManager.logAndSendExceptionMessage(throwable)
        }
    }

    private val profileUpdateInitCoroutineName = "profile_refresh_init"

    init {
        viewModelScope.launch(CoroutineName(profileUpdateInitCoroutineName) + profileUpdateErrorHandler) {
            profileSharedFlow.emit(profile)
            updateProfileIfOutdated()
        }
    }

    fun updateProfile() {
        viewModelScope.launch(profileUpdateErrorHandler) {
            updateProfileIfOutdated { messageManager.sendMessage(profileAlreadyUpdatedMessage()) }
        }
    }

    private suspend inline fun updateProfileIfOutdated(doIfProfileAlreadyUpdated: () -> Unit = {}) {
        _isProfileUpdating.value = true
        if (isProfileUpdated(profile)) {
            doIfProfileAlreadyUpdated()
        } else {
            updateProfileAndSendToFlow()
        }
        _isProfileUpdating.value = false
    }

    private suspend fun updateProfileAndSendToFlow() {
        profile = updateProfile(profile)
        profileSharedFlow.emit(profile)
    }

    fun onOpenProfileInBrowserClicked() {
        viewModelScope.launch { openUrlEventFlow.emit(profile.url) }
    }
}
