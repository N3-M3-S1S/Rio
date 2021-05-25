package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.domain.profile.Profile
import kotlinx.datetime.LocalDateTime

interface ProfileLastCrawlDateTimeProvider<P : Profile> {
    suspend fun getProfileLastCrawlDateTime(profile: P): LocalDateTime
}
