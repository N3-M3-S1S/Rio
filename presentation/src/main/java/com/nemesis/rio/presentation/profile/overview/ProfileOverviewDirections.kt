package com.nemesis.rio.presentation.profile.overview

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.presentation.profile.character.CharacterParcel
import com.nemesis.rio.presentation.profile.guild.GuildParcel
import com.nemesis.rio.presentation.profile.overview.character.CharacterOverviewParentFragmentDirections
import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragmentDirections

fun Profile.getProfileOverviewDirection() = when (this) {
    is Character -> CharacterOverviewParentFragmentDirections.toCharacterOverview(CharacterParcel(this))
    is Guild -> GuildOverviewParentFragmentDirections.toGuildOverview(GuildParcel(this))
    else -> error("Unknown profile class: ${this::class.simpleName}")
}
