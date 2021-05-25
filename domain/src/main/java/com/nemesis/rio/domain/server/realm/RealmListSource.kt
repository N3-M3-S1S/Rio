package com.nemesis.rio.domain.server.realm

import com.nemesis.rio.domain.server.Region

interface RealmListSource {
    suspend fun getRealmListForRegion(region: Region): List<Realm>
}
