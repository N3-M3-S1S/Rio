package com.nemesis.rio.presentation.profile.overview.guild.ranks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.LoadingStateDelegate
import com.nemesis.rio.presentation.utils.extensions.toLiveData
import com.nemesis.rio.presentation.utils.mapWithDelayedLoading
import kotlinx.coroutines.flow.Flow

class GuildRaidRanksViewModel(
    guildFlow: Flow<Guild>,
    guildRaidRanksListDataFactory: GuildRaidRanksListDataFactory,
    loadingStateController: LoadingStateController,
) : ViewModel(), LoadingStateDelegate by loadingStateController {

    val guildRaidRanksListData: LiveData<GuildRaidRanksListData> =
        guildFlow.mapWithDelayedLoading(
            loadingStateController,
            viewModelScope,
            guildRaidRanksListDataFactory::getGuildRaidRanksListData
        ).toLiveData(viewModelScope)
}
