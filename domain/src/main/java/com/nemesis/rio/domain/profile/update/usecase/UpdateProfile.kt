package com.nemesis.rio.domain.profile.update.usecase

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.update.ProfileUpdateRegistry

class UpdateProfile<P : Profile>(
    private val profileUpdateStrategy: ProfileUpdateStrategy<P>,
    private val profileUpdateRegistry: ProfileUpdateRegistry<P>,
) {

    suspend operator fun invoke(profile: P) = profileUpdateStrategy.update(profile).also {
        profileUpdateRegistry.registerProfileUpdated(it)
    }
}
