package com.nemesis.rio.presentation.profile.overview.guild.raiding

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressListData
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressViewModel
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.extensions.toLiveData
import com.nemesis.rio.presentation.utils.mapWithDelayedLoading
import kotlinx.coroutines.flow.Flow

class GuildRaidProgressViewModel(
    guildFlow: Flow<Guild>,
    guildRaidProgressListDataFactory: GuildRaidProgressListDataFactory,
    loadingStateController: LoadingStateController,
) : RaidProgressViewModel(loadingStateController) {

    override val raidProgressListData: LiveData<RaidProgressListData> =
        guildFlow.mapWithDelayedLoading(
            loadingStateController,
            viewModelScope,
            guildRaidProgressListDataFactory::getGuildRaidProgressListData
        ).toLiveData(viewModelScope)
}
