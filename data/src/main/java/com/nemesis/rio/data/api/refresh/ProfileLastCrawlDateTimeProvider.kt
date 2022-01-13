package com.nemesis.rio.data.api.refresh

import com.nemesis.rio.domain.profile.Profile
import kotlinx.datetime.Instant

interface ProfileLastCrawlDateTimeProvider<P : Profile> {
    suspend fun getProfileLastCrawlInstant(profile: P): Instant
}
