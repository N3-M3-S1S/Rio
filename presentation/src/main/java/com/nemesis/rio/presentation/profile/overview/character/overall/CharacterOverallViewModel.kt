package com.nemesis.rio.presentation.profile.overview.character.overall

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.guild.search.usecase.SearchGuild
import com.nemesis.rio.presentation.app.messages.MessageManager
import com.nemesis.rio.presentation.app.messages.logAndSendExceptionMessage
import com.nemesis.rio.presentation.app.messages.profileNotFoundMessage
import com.nemesis.rio.presentation.profile.ProfileType
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.LoadingStateDelegate
import com.nemesis.rio.presentation.utils.extensions.toLiveData
import com.nemesis.rio.presentation.utils.mapWithDelayedLoading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterOverallViewModel(
    private val characterOverallDataFactory: CharacterOverallDataFactory,
    private val characterFlow: Flow<Character>,
    private val searchGuild: SearchGuild,
    private val navigateToProfileOverviewEventFlow: MutableSharedFlow<Profile>,
    private val messageManager: MessageManager,
    private val loadingStateController: LoadingStateController,
    private val guildSearchActiveStateController: LoadingStateController
) : ViewModel(), LoadingStateDelegate by loadingStateController {

    val characterOverallData: LiveData<CharacterOverallData> =
        characterFlow.mapWithDelayedLoading(
            loadingStateController,
            viewModelScope,
            characterOverallDataFactory::getCharacterOverallData
        ).toLiveData(viewModelScope)

    val isGuildSearchActive: LiveData<Boolean> = guildSearchActiveStateController.isLoading

    private val guildSearchExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        messageManager.logAndSendExceptionMessage(throwable)
        guildSearchActiveStateController.setLoading(false)
    }

    fun onGuildNameClicked() {
        viewModelScope.launch(guildSearchExceptionHandler) {
            val character = characterFlow.first()
            guildSearchActiveStateController.setLoadingDelayed(true, viewModelScope)
            val guild =
                searchGuild(
                    character.guildName!!,
                    character.region,
                    character.realm
                )
            guildSearchActiveStateController.setLoadingDelayed(false, viewModelScope)
            guild?.let { navigateToProfileOverviewEventFlow.emit(it) } ?: handleGuildNotFound()
        }
    }

    private fun handleGuildNotFound() {
        logUnexpectedNotFoundGuild()
        messageManager.sendMessage(profileNotFoundMessage(ProfileType.GUILD))
    }

    private fun logUnexpectedNotFoundGuild() {
        viewModelScope.launch {
            val guildName = characterFlow.first().guildName
            Timber.w("Character has a guild name '$guildName', but a guild with this name cannot be found")
        }
    }
}
