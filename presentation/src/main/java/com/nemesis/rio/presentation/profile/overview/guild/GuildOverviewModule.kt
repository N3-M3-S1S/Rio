package com.nemesis.rio.presentation.profile.overview.guild

import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.overview.guild.overall.GuildOverallFragment
import com.nemesis.rio.presentation.profile.overview.guild.overall.guildOverallModule
import com.nemesis.rio.presentation.profile.overview.guild.raiding.GuildRaidProgressFragment
import com.nemesis.rio.presentation.profile.overview.guild.raiding.guildRaidProgressModule
import com.nemesis.rio.presentation.profile.overview.guild.ranks.GuildRaidRanksFragment
import com.nemesis.rio.presentation.profile.overview.guild.ranks.guildRaidRanksModule
import com.nemesis.rio.presentation.profile.overview.profileOverviewCoreDependencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import kotlin.reflect.KClass

@OptIn(ExperimentalCoroutinesApi::class)
val guildOverviewModule = module {
    scope<GuildOverviewParentFragment> {
        profileOverviewCoreDependencies<Guild>()
        factory { createGuildOverviewChildFragmentNavigator() }
    }
}

private fun createGuildOverviewChildFragmentNavigator(): com.nemesis.rio.presentation.profile.overview.ProfileOverviewChildFragmentsNavigator {
    val menuId = R.menu.fragment_guild_overview_bottom_bar_menu
    val menuItemIdToFragmentClass = SparseArray<KClass<out Fragment>>(3).apply {
        append(R.id.guild_overview_overall, GuildOverallFragment::class)
        append(R.id.guild_overview_raid_ranks, GuildRaidRanksFragment::class)
        append(R.id.guild_overview_raid_progress, GuildRaidProgressFragment::class)
    }
    return com.nemesis.rio.presentation.profile.overview.ProfileOverviewChildFragmentsNavigator(
        menuId,
        menuItemIdToFragmentClass
    )
}

val guildOverviewModules =
    listOf(guildOverviewModule, guildOverallModule, guildRaidRanksModule, guildRaidProgressModule)
