package com.nemesis.rio.presentation.profile

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.ProfileSaver
import com.nemesis.rio.data.profile.update.ProfileUpdateRegistryImpl
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.profile.update.ProfileUpdateRegistry
import com.nemesis.rio.domain.profile.update.usecase.CheckIsProfileUpdated
import com.nemesis.rio.domain.profile.update.usecase.GetProfileLastUpdateDateTime
import com.nemesis.rio.domain.profile.update.usecase.UpdateProfile
import com.nemesis.rio.presentation.profile.character.characterQualifier
import com.nemesis.rio.presentation.profile.guild.guildQualifier
import org.koin.core.module.Module

inline fun <reified P : Profile> Module.profileCoreDependencies() {
    val profileQualifier = profileQualifier<P>()

    factory(profileQualifier) {
        ProfileIDProvider<P>(profileDao = get(profileQualifier), profileIDCache = get())
    }

    factory(profileQualifier) {
        ProfileSaver<P>(profileDao = get(profileQualifier), profileIdCache = get())
    }

    factory<ProfileUpdateRegistry<P>>(profileQualifier) {
        ProfileUpdateRegistryImpl(
            profileDao = get(profileQualifier),
            profileIDProvider = get(profileQualifier),
            profileLastCrawlDateTimeProvider = get(profileQualifier)
        )
    }

    factory(profileQualifier) {
        UpdateProfile<P>(
            profileUpdateStrategy = get(profileQualifier),
            profileUpdateRegistry = get(profileQualifier)
        )
    }

    factory(profileQualifier) {
        GetProfileLastUpdateDateTime<P>(profileRefreshRegistry = get(profileQualifier))
    }

    factory(profileQualifier) {
        CheckIsProfileUpdated<P>(
            profileRefreshRegistry = get(
                profileQualifier
            )
        )
    }
}

inline fun <reified P : Profile> profileQualifier() = when (P::class) {
    Character::class -> characterQualifier
    Guild::class -> guildQualifier
    else -> error("Unknown profile class: ${P::class.simpleName}")
}
