package com.nemesis.rio.presentation.profile.search

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.usecase.GetRealmListForRegion
import com.nemesis.rio.presentation.profile.ProfileType
import com.nemesis.rio.presentation.utils.sharedPreferencesStringToMap
import com.nemesis.rio.presentation.utils.toSharedPreferencesString
import com.nemesis.rio.utils.enumMap

class ProfileSearchPreferences(private val getRealmListForRegion: GetRealmListForRegion) :
    KotprefModel() {

    var region by enumValuePref(Region.EU)
    var profileType by enumValuePref(ProfileType.CHARACTER)
    private var selectedRegionAndRealmSharedPreferencesString by stringPref()

    fun saveSelectedRegionAndRealm(selectedRegionAndRealm: Map<Region, String>) {
        val sharedPreferencesString =
            selectedRegionAndRealm.toSharedPreferencesString(Region::name) { it }
        selectedRegionAndRealmSharedPreferencesString = sharedPreferencesString
    }

    suspend fun getSelectedRegionAndRealm(): MutableMap<Region, String> {
        val regionToLastSelectedRealm = sharedPreferencesStringToMap<Region, String>(
            selectedRegionAndRealmSharedPreferencesString,
            ::enumValueOf,
            { it },
            enumMap()
        )
        setDefaultRealmForRegionIfAbsent(regionToLastSelectedRealm)
        return regionToLastSelectedRealm
    }

    private suspend fun setDefaultRealmForRegionIfAbsent(selectedRegionAndRealm: MutableMap<Region, String>) {
        enumValues<Region>().forEach { region ->
            selectedRegionAndRealm.getOrPut(region) { getRealmListForRegion(region).first() }
        }
    }
}
