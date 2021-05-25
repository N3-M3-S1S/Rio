package com.nemesis.rio.presentation.server.realm

import com.nemesis.rio.domain.server.realm.Realm

typealias RealmSelectData = Map<Char, List<Realm>>

fun realmSelectData(realmList: List<Realm>) = realmList.groupBy { it[0] }
