package com.nemesis.rio.presentation.profile.search.history

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class CombinedProfileSearchHistoryListItemsSource(private val sources: List<ProfileSearchHistoryListItemsSource>) :
    ProfileSearchHistoryListItemsSource {

    override fun getProfileSearchHistoryItemsFlow(): Flow<List<ProfileSearchHistoryListItem>> =
        sources
            .map { source -> source.getProfileSearchHistoryItemsFlow() }
            .let { searchHistoryFlows ->
                combine(searchHistoryFlows) { searchHistories ->
                    searchHistories.toList().flatten().sortedByDescending { it.lastSearchDateTime }
                }
            }
}
