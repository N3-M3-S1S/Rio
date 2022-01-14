package com.nemesis.rio.domain.profile.search

import com.nemesis.rio.domain.profile.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileSearchHistoryRepository<P : Profile> {
    suspend fun add(profile: P)
    suspend fun remove(profile: P)
    fun getSearchHistory(): Flow<ProfileSearchHistory<P>>
}
