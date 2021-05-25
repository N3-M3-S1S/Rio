package com.nemesis.rio.presentation.profile.overview.guild

import android.os.Bundle
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.presentation.profile.overview.ProfileOverviewParentFragment

class GuildOverviewParentFragment : ProfileOverviewParentFragment<Guild>() {

    override fun getProfileFromArguments(arguments: Bundle): Guild =
        GuildOverviewParentFragmentArgs.fromBundle(arguments).guildParcel.guild
}
