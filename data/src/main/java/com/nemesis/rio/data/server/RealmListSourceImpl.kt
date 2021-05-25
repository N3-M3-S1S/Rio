package com.nemesis.rio.data.server

import android.content.res.Resources
import com.nemesis.rio.data.R
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.domain.server.realm.RealmListSource

class RealmListSourceImpl(private val resources: Resources) : RealmListSource {
    override suspend fun getRealmListForRegion(region: Region): List<Realm> {
        val realmListArrayResId = when (region) {
            Region.EU -> R.array.realm_list_eu
            Region.US -> R.array.realm_list_us
            Region.KR -> R.array.realm_list_kr
            Region.TW -> R.array.realm_list_tw
        }
        return resources.getStringArray(realmListArrayResId).sorted()
    }
}
