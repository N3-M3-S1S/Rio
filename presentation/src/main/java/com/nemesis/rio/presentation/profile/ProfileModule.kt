package com.nemesis.rio.presentation.profile

import com.nemesis.rio.data.profile.database.ProfileIDCache
import com.nemesis.rio.presentation.profile.character.characterModule
import com.nemesis.rio.presentation.profile.guild.guildModule
import org.koin.dsl.module
import org.koin.experimental.builder.single

val profileModule = module {
    single<ProfileIDCache>()
}

val profileModules = listOf(profileModule, guildModule, characterModule)
