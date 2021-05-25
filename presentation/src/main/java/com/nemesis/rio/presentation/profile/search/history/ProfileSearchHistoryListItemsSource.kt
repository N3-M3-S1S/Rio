package com.nemesis.rio.presentation.profile.search.history

import kotlinx.coroutines.flow.Flow

interface ProfileSearchHistoryListItemsSource {
    fun getProfileSearchHistoryItemsFlow(): Flow<List<ProfileSearchHistoryListItem>>
}
