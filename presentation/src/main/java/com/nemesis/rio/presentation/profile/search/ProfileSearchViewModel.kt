package com.nemesis.rio.presentation.profile.search

import androidx.lifecycle.*
import com.chibatching.kotpref.bulk
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.character.search.usecase.SearchCharacter
import com.nemesis.rio.domain.profile.guild.search.usecase.SearchGuild
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.domain.server.realm.usecase.GetRealmListForRegion
import com.nemesis.rio.presentation.app.messages.MessageManager
import com.nemesis.rio.presentation.app.messages.logAndSendExceptionMessage
import com.nemesis.rio.presentation.app.messages.profileNotFoundMessage
import com.nemesis.rio.presentation.profile.ProfileType
import com.nemesis.rio.presentation.utils.extensions.notNullValue
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileSearchViewModel(
    private val searchPreferences: ProfileSearchPreferences,
    private val searchCharacter: SearchCharacter,
    private val searchGuild: SearchGuild,
    private val getRealmListForRegion: GetRealmListForRegion,
    private val navigateToProfileOverviewScreenEventFlow: MutableSharedFlow<Profile>,
    private val messageManager: MessageManager,
) : ViewModel(), DefaultLifecycleObserver {
    val profileName = MutableLiveData<String>()
    val profileType = MutableLiveData(searchPreferences.profileType)

    private val _selectedRegion = MutableLiveData(searchPreferences.region)
    val selectedRegion: LiveData<Region> = _selectedRegion

    private val _selectedRealm = MutableLiveData<String>()
    val selectedRealm: LiveData<String> = _selectedRealm

    private val _isSearchActive = MutableLiveData(false)
    val isSearchActive: LiveData<Boolean> = _isSearchActive

    private val _isSearchEnabled = MediatorLiveData<Boolean>()
    val isSearchEnabled: LiveData<Boolean> = _isSearchEnabled

    private var selectedRegionToRealm: MutableMap<Region, String>

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
        setupIsSearchEnabledLiveData()
        runBlocking {
            selectedRegionToRealm = searchPreferences.getSelectedRegionAndRealm()
            _selectedRealm.value = selectedRegionToRealm[selectedRegion.notNullValue]
        }
    }

    private fun setupIsSearchEnabledLiveData() {
        with(_isSearchEnabled) {
            value = false
            addSource(_isSearchActive) { searchActive ->
                _isSearchEnabled.value = !searchActive && isProfileNameValid()
            }
            addSource(profileName) {
                _isSearchEnabled.value = !_isSearchActive.notNullValue && isProfileNameValid()
            }
        }
    }

    // TODO: move profile name validation to domain layer
    private fun isProfileNameValid() =
        profileName.value != null && profileName.notNullValue.length >= 2

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
        val name = profileName.notNullValue
        val region = selectedRegion.notNullValue
        val realm = selectedRealm.notNullValue

        @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
        return when (profileType.notNullValue) {
            ProfileType.CHARACTER -> searchCharacter(name, region, realm)
            ProfileType.GUILD -> searchGuild(name, region, realm)
        }
    }

    private suspend fun sendNavigateToProfileOverviewScreenEvent(profile: Profile) {
        navigateToProfileOverviewScreenEventFlow.emit(profile)
    }

    private fun sendProfileNotFoundMessage() {
        messageManager.sendMessage(profileNotFoundMessage(profileType.notNullValue))
    }

    override fun onStop(owner: LifecycleOwner) {
        saveSearchPreferences()
    }

    private fun saveSearchPreferences() {
        searchPreferences.bulk {
            profileType = this@ProfileSearchViewModel.profileType.notNullValue
            region = this@ProfileSearchViewModel.selectedRegion.notNullValue
            saveSelectedRegionAndRealm(selectedRegionToRealm)
        }
    }

    fun onRegionSelectClicked() {
        sendSelectRegionEvent()
    }

    private fun sendSelectRegionEvent() {
        viewModelScope.launch {
            _profileSearchOptionSelectEvent.emit(SelectRegion(selectedRegion.notNullValue))
        }
    }

    fun onRegionChanged(region: Region) {
        _selectedRegion.value = region
        _selectedRealm.value = selectedRegionToRealm[region]
    }

    fun onRealmSelectClicked() {
        sendSelectRealmEvent()
    }

    private fun sendSelectRealmEvent() {
        viewModelScope.launch {
            _profileSearchOptionSelectEvent
                .emit(
                    SelectRealm(
                        getRealmListForRegion(selectedRegion.notNullValue),
                        selectedRealm.notNullValue
                    )
                )
        }
    }

    fun onRealmChanged(realm: Realm) {
        _selectedRealm.value = realm
        selectedRegionToRealm[selectedRegion.notNullValue] = realm
    }
}
