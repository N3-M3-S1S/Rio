package com.nemesis.rio.domain.profile.update.usecase

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.update.ProfileUpdateRegistry

class CheckIsProfileUpdated<P : Profile>(private val profileRefreshRegistry: ProfileUpdateRegistry<P>) {

    suspend operator fun invoke(profile: P): Boolean =
        profileRefreshRegistry.isProfileUpdated(profile)
}
