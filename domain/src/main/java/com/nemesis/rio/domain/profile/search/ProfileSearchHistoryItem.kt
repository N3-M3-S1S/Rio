package com.nemesis.rio.domain.profile.search

import com.nemesis.rio.domain.profile.Profile
import kotlinx.datetime.Instant

data class ProfileSearchHistoryItem<P : Profile>(
    val profile: P,
    val lastSearch: Instant,
)
