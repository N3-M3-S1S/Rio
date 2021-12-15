package com.nemesis.rio.presentation.profile.search

import androidx.lifecycle.*
import com.chibatching.kotpref.bulk
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.character.search.usecase.SearchCharacter
import com.nemesis.rio.domain.profile.guild.search.usecase.SearchGuild
import com.nemesis.rio.domain.profile.search.usecase.ValidateProfileName
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.domain.server.realm.usecase.GetRealmListForRegion
import com.nemesis.rio.presentation.app.messages.MessageManager
import com.nemesis.rio.presentation.app.messages.logAndSendExceptionMessage
import com.nemesis.rio.presentation.app.messages.profileNotFoundMessage
import com.nemesis.rio.presentation.profile.ProfileType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileSearchViewModel(
    private val searchPreferences: ProfileSearchPreferences,
    private val validateProfileName: ValidateProfileName,
    private val searchCharacter: SearchCharacter,
    private val searchGuild: SearchGuild,
    private val getRealmListForRegion: GetRealmListForRegion,
    private val navigateToProfileOverviewScreenEventFlow: MutableSharedFlow<Profile>,
    private val messageManager: MessageManager,
) : ViewModel(), DefaultLifecycleObserver {
    val profileName = MutableStateFlow("")
    val profileType = MutableStateFlow(searchPreferences.profileType)

    private val _selectedRegion = MutableStateFlow(searchPreferences.region)
    val selectedRegion: StateFlow<Region> = _selectedRegion

    private val _selectedRealm: MutableStateFlow<Realm> = MutableStateFlow("")
    val selectedRealm: StateFlow<String> = _selectedRealm

    private var selectedRegionToRealm: MutableMap<Region, String>

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    val isSearchEnabled: StateFlow<Boolean> =
        profileName.combine(_isSearchActive) { profileName, isSearchActive ->
            !isSearchActive && validateProfileName(profileName)
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val profileSearchExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _isSearchActive.value = false
            messageManager.logAndSendExceptionMessage(throwable)
        }

    private val _profileSearchOptionSelectEvent =
        MutableSharedFlow<ProfileSearchOptionSelectEvent>()
    val profileSearchOptionSelectEvent: Flow<ProfileSearchOptionSelectEvent> =
        _profileSearchOptionSelectEvent

    init {
        runBlocking {
            selectedRegionToRealm = searchPreferences.getSelectedRegionAndRealm()
            _selectedRealm.value = selectedRegionToRealm.getValue(_selectedRegion.value)
        }
    }

    fun search() {
        viewModelScope.launch(profileSearchExceptionHandler) {
            _isSearchActive.value = true
            val profile = searchProfile()

            if (profile != null) {
                sendNavigateToProfileOverviewScreenEvent(profile)
            } else {
                sendProfileNotFoundMessage()
            }

            _isSearchActive.value = false
        }
    }

    private suspend fun searchProfile(): Profile? {
        val name = profileName.value
        val region = selectedRegion.value
        val realm = selectedRealm.value

        @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
        return when (profileType.value) {
            ProfileType.CHARACTER -> searchCharacter(name, region, realm)
            ProfileType.GUILD -> searchGuild(name, region, realm)
        }
    }

    private suspend fun sendNavigateToProfileOverviewScreenEvent(profile: Profile) {
        navigateToProfileOverviewScreenEventFlow.emit(profile)
    }

    private fun sendProfileNotFoundMessage() {
        messageManager.sendMessage(profileNotFoundMessage(profileType.value))
    }

    override fun onStop(owner: LifecycleOwner) {
        saveSearchPreferences()
    }

    private fun saveSearchPreferences() {
        searchPreferences.bulk {
            profileType = this@ProfileSearchViewModel.profileType.value
            region = this@ProfileSearchViewModel.selectedRegion.value
            saveSelectedRegionAndRealm(selectedRegionToRealm)
        }
    }

    fun onRegionSelectClicked() {
        sendSelectRegionEvent()
    }

    private fun sendSelectRegionEvent() {
        viewModelScope.launch {
            _profileSearchOptionSelectEvent.emit(SelectRegion(selectedRegion.value))
        }
    }

    fun onRegionChanged(region: Region) {
        _selectedRegion.value = region
        _selectedRealm.value = selectedRegionToRealm.getValue(region)
    }

    fun onRealmSelectClicked() {
        sendSelectRealmEvent()
    }

    private fun sendSelectRealmEvent() {
        viewModelScope.launch {
            _profileSearchOptionSelectEvent
                .emit(
                    SelectRealm(
                        getRealmListForRegion(selectedRegion.value),
                        selectedRealm.value
                    )
                )
        }
    }

    fun onRealmChanged(realm: Realm) {
        _selectedRealm.value = realm
        selectedRegionToRealm[selectedRegion.value] = realm
    }
}
