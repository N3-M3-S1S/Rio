package com.nemesis.rio.presentation.profile.overview.character.raiding

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressListData
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressViewModel
import com.nemesis.rio.presentation.utils.LoadingStateController
import com.nemesis.rio.presentation.utils.extensions.toLiveData
import com.nemesis.rio.presentation.utils.mapWithDelayedLoading
import kotlinx.coroutines.flow.Flow

class CharacterRaidProgressViewModel(
    characterFlow: Flow<Character>,
    characterRaidProgressListDataFactory: CharacterRaidProgressListDataFactory,
    loadingStateController: LoadingStateController
) : RaidProgressViewModel(loadingStateController) {

    override val raidProgressListData: LiveData<RaidProgressListData> =
        characterFlow.mapWithDelayedLoading(
            loadingStateController,
            viewModelScope,
            characterRaidProgressListDataFactory::getCharacterRaidProgressListData
        ).toLiveData(viewModelScope)
}
