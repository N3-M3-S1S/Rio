package com.nemesis.rio.presentation.profile.overview.character.mplus.runs

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun

interface CharacterMythicPlusRunsDataActionsHandler {
    fun onRunsSortingOptionClicked()
    fun onRunsSortingOrderClicked()
    fun onOpenRunInBrowserClicked(run: MythicPlusRun)
}
