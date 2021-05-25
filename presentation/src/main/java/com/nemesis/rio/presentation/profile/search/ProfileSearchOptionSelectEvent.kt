package com.nemesis.rio.presentation.profile.search

import com.nemesis.rio.domain.server.Region

sealed class ProfileSearchOptionSelectEvent

data class SelectRegion(val selectedRegion: Region) : ProfileSearchOptionSelectEvent()

data class SelectRealm(val realmList: List<String>, val selectedRealm: String) :
    ProfileSearchOptionSelectEvent()
