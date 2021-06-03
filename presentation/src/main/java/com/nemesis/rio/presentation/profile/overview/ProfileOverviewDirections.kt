package com.nemesis.rio.presentation.profile.overview

import androidx.navigation.NavDirections
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.presentation.profile.character.CharacterParcel
import com.nemesis.rio.presentation.profile.guild.GuildParcel
import com.nemesis.rio.presentation.profile.overview.character.CharacterOverviewParentFragmentDirections
import com.nemesis.rio.presentation.profile.overview.guild.GuildOverviewParentFragmentDirections

val Profile.overviewDirection: NavDirections
    get() = when (this) {
        is Character -> CharacterOverviewParentFragmentDirections
            .toCharacterOverview(CharacterParcel(this))
        is Guild -> GuildOverviewParentFragmentDirections.toGuildOverview(GuildParcel(this))
    }
