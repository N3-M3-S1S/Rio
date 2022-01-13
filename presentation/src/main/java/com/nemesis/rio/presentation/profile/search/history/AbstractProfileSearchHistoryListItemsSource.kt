package com.nemesis.rio.presentation.profile.search.history

import com.airbnb.epoxy.IdUtils
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryItem
import com.nemesis.rio.domain.profile.search.usecase.GetProfileSearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class AbstractProfileSearchHistoryListItemsSource<P : Profile>(
    private val getProfileSearchHistory: GetProfileSearchHistory<P>,
    private val profileSearchHistoryActionsHandler: ProfileSearchHistoryItemActionsHandler<P>
) : ProfileSearchHistoryListItemsSource {

    override fun getProfileSearchHistoryItemsFlow(): Flow<List<ProfileSearchHistoryListItem>> {
        return getProfileSearchHistory()
            .map { searchHistoryList ->
                searchHistoryList.map { searchHistoryItem ->
                    mapProfileSearchHistoryItemToListItem(searchHistoryItem)
                }
            }
    }

    private fun mapProfileSearchHistoryItemToListItem(
        profileSearchHistoryItem: ProfileSearchHistoryItem<P>,
    ): ProfileSearchHistoryListItem {
        val profile = profileSearchHistoryItem.profile
        val id = getProfileId(profile)
        val onClick = { profileSearchHistoryActionsHandler.onClicked(profile) }
        val onSwipe = { profileSearchHistoryActionsHandler.onRemoved(profile) }
        val profileDescription = getProfileDescription(profile)
        val serverAndFactionText = getServerAndFactionText(profile)

        return ProfileSearchHistoryListItem(
            id,
            profile.name,
            profileDescription,
            serverAndFactionText,
            profileSearchHistoryItem.lastSearch,
            onClick,
            onSwipe
        )
    }

    private fun getProfileId(profile: P): Long {
        return IdUtils.hashString64Bit(profile.name) + profile::class.hashCode()
    }

    protected abstract fun getProfileDescription(profile: P): CharSequence

    protected abstract fun getServerAndFactionText(profile: P): CharSequence
}
