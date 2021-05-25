package com.nemesis.rio.presentation.server.realm

import com.airbnb.epoxy.Typed2EpoxyController
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.presentation.ItemStickyHeaderLetterBindingModel_
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.itemStickyHeaderLetter

class RealmSelectDataController(private val onRealmSelected: (String) -> Unit) :
    Typed2EpoxyController<RealmSelectData, Realm>() {

    override fun buildModels(data: RealmSelectData, selectedRealm: Realm) {
        data.forEach { (letter, realms) ->

            itemStickyHeaderLetter {
                id(letter.hashCode())
                letter(letter)
            }

            realms.forEach { realm ->
                itemOption {
                    id(realm.hashCode())
                    string(realm)
                    onClick(onRealmSelected)
                    isSelected(realm == selectedRealm)
                }
            }
        }
    }

    override fun isStickyHeader(position: Int): Boolean =
        adapter.getModelAtPosition(position) is ItemStickyHeaderLetterBindingModel_
}
