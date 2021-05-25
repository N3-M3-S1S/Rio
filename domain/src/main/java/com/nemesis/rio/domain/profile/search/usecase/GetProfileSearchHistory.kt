package com.nemesis.rio.domain.profile.search.usecase

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistorySource

class GetProfileSearchHistory<P : Profile>(private val profileSearchHistorySource: ProfileSearchHistorySource<P>) {

    operator fun invoke() = profileSearchHistorySource.getSearchHistory()
}
