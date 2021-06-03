package com.nemesis.rio.presentation.profile.overview.guild.overall

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chibatching.kotpref.livedata.asLiveData
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.presentation.ranks.RanksSpan
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.LoadingStateDelegate
import com.nemesis.rio.presentation.utils.extensions.toLiveData
import com.nemesis.rio.presentation.utils.mapWithDelayedLoading
import kotlinx.coroutines.flow.Flow

class GuildOverallViewModel(
    guildFlow: Flow<Guild>,
    private val guildOverallDataFactory: GuildOverallDataFactory,
    private val loadingStateController: LoadingStateController,
) : ViewModel(), LoadingStateDelegate by loadingStateController {

    val ranksSpan: LiveData<RanksSpan> =
        GuildOverallPreferences.asLiveData(GuildOverallPreferences::ranksSpan)

    val guildOverallData: LiveData<GuildOverallData> = guildFlow.mapWithDelayedLoading(
        loadingStateController,
        viewModelScope,
        guildOverallDataFactory::getGuildOverallData
    ).toLiveData(viewModelScope)

    fun rankSpanChanged(ranksSpan: RanksSpan) {
        GuildOverallPreferences.ranksSpan = ranksSpan
    }
}
