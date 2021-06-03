package com.nemesis.rio.presentation.profile.search.guild.history.list

import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.profile.search.usecase.GetProfileSearchHistory
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.buildServerInfoString
import com.nemesis.rio.presentation.profile.search.history.AbstractProfileSearchHistoryListItemsSource
import com.nemesis.rio.presentation.profile.search.history.ProfileSearchHistoryItemActionsHandler
import splitties.resources.appStr

class GuildSearchHistoryListItemsSource(
    getProfileSearchHistory: GetProfileSearchHistory<Guild>,
    guildSearchHistoryActionsHandler: ProfileSearchHistoryItemActionsHandler<Guild>
) : AbstractProfileSearchHistoryListItemsSource<Guild>(
    getProfileSearchHistory,
    guildSearchHistoryActionsHandler
) {
    override fun getProfileDescription(profile: Guild): CharSequence = appStr(R.string.profile_guild)

    override fun getServerAndFactionText(profile: Guild): CharSequence =
        with(profile) { buildServerInfoString(region, realm, faction) }
}
