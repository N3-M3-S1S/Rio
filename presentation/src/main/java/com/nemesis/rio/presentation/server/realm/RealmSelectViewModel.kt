package com.nemesis.rio.presentation.server.realm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.domain.server.realm.usecase.FilterRealmList
import kotlinx.coroutines.launch

class RealmSelectViewModel(
    private val realmList: List<Realm>,
    private val filterRealmList: FilterRealmList,
) : ViewModel() {

    private val _realmSelectData = MutableLiveData(realmSelectData(realmList))
    val realmSelectData: LiveData<RealmSelectData> = _realmSelectData

    fun filterRealmList(query: String) {
        viewModelScope.launch {
            val filteredRealmList = filterRealmList(query, realmList)
            _realmSelectData.value = realmSelectData(filteredRealmList)
        }
    }
}
