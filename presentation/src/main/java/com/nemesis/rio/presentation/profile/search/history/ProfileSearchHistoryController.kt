package com.nemesis.rio.presentation.profile.search.history

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemCenterText
import com.nemesis.rio.presentation.itemProfileSearchHistory

class ProfileSearchHistoryController :
    TypedEpoxyController<List<ProfileSearchHistoryListItem>>() {
    private lateinit var data: List<ProfileSearchHistoryListItem>

    override fun buildModels(data: List<ProfileSearchHistoryListItem>?) {
        if (data.isNullOrEmpty()) {
            this.data = emptyList()
            searchHistoryIsEmptyMessage()
        } else {
            this.data = data
            searchHistory(data)
        }
    }

    private fun EpoxyController.searchHistoryIsEmptyMessage() {
        itemCenterText {
            id(-1)
            textResId(R.string.search_history_empty)
        }
    }

    fun onSearchHistoryItemSwiped(position: Int) {
        data[position].onSwipe()
    }

    private fun EpoxyController.searchHistory(searchHistory: List<ProfileSearchHistoryListItem>) {
        searchHistory.forEach { item ->
            itemProfileSearchHistory {
                id(item.id)
                profileName(item.name)
                profileDescription(item.profileDescription)
                serverInfo(item.serverInfo)
                onClick(item.onClick)
            }
        }
    }
}
