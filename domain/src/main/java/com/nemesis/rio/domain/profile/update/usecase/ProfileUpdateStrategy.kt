package com.nemesis.rio.domain.profile.update.usecase

import com.nemesis.rio.domain.profile.Profile

interface ProfileUpdateStrategy<P : Profile> {
    suspend fun update(profile: P): P
}
