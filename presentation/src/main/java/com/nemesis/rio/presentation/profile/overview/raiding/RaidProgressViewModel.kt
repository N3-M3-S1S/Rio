package com.nemesis.rio.presentation.profile.overview.raiding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nemesis.rio.presentation.utils.LoadingStateDelegate

abstract class RaidProgressViewModel(loadingStateDelegate: LoadingStateDelegate) :
    ViewModel(), LoadingStateDelegate by loadingStateDelegate {

    abstract val raidProgressListData: LiveData<RaidProgressListData>
}
