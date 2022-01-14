package com.nemesis.rio.domain.profile.search.usecase

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistory
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetProfileSearchHistory<P : Profile>(private val profileSearchHistoryRepository: ProfileSearchHistoryRepository<P>) {

    operator fun invoke(): Flow<ProfileSearchHistory<P>> =
        profileSearchHistoryRepository.getSearchHistory()
}
