package com.nemesis.rio.domain.server.realm.usecase

import com.nemesis.rio.domain.server.realm.Realm

class FilterRealmList {

    operator fun invoke(realmName: String, realmList: List<Realm>) =
        if (realmName.isEmpty()) {
            realmList
        } else {
            realmList.filter { it.contains(realmName, true) }
        }
}
