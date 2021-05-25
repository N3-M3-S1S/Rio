package com.nemesis.rio.domain.profile.update

import com.nemesis.rio.domain.profile.Profile
import kotlinx.datetime.LocalDateTime

interface ProfileUpdateRegistry<P : Profile> {
    suspend fun registerProfileUpdated(profile: P)
    suspend fun getLastUpdateDateTime(profile: P): LocalDateTime
    suspend fun isProfileUpdated(profile: P): Boolean
}
