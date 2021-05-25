package com.nemesis.rio.domain.profile.search

import com.nemesis.rio.domain.profile.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileSearchHistorySource<P : Profile> {
    suspend fun addOrUpdate(profile: P)
    suspend fun remove(profile: P)
    fun getSearchHistory(): Flow<List<ProfileSearchHistoryItem<P>>>
}
