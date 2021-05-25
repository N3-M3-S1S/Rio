package com.nemesis.rio.domain.server.realm.usecase

import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.domain.server.realm.RealmListSource

class GetRealmListForRegion(private val realmListSource: RealmListSource) {

    suspend operator fun invoke(region: Region): List<Realm> {
        return realmListSource.getRealmListForRegion(region)
    }
}
