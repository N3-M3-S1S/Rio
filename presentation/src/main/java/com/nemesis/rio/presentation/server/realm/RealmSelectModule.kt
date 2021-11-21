package com.nemesis.rio.presentation.server.realm

import com.nemesis.rio.data.server.RealmListSourceImpl
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.domain.server.realm.RealmListSource
import com.nemesis.rio.domain.server.realm.usecase.FilterRealmList
import com.nemesis.rio.domain.server.realm.usecase.GetRealmListForRegion
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.factory
import org.koin.dsl.module

val realmSelectModule = module {
    factory<GetRealmListForRegion>()
    factory<FilterRealmList>()
    factory<RealmListSource> { RealmListSourceImpl(androidContext().resources) }
    factory { (onRealmSelected: ((String) -> Unit)) -> RealmSelectDataController(onRealmSelected) }
    viewModel { (realmList: List<Realm>) ->
        RealmSelectViewModel(
            realmList = realmList,
            filterRealmList = get()
        )
    }
}
