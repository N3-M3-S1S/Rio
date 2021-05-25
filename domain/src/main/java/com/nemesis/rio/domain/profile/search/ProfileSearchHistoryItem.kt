package com.nemesis.rio.domain.profile.search

import com.nemesis.rio.domain.profile.Profile
import kotlinx.datetime.LocalDateTime

data class ProfileSearchHistoryItem<P : Profile>(
    val profile: P,
    val lastSearchDateTime: LocalDateTime,
)
