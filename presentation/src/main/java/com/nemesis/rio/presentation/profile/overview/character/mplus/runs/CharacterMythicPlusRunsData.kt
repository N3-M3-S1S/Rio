package com.nemesis.rio.presentation.profile.overview.character.mplus.runs

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.sorting.SortingOrder

data class CharacterMythicPlusRunsData(
    val selectedSortingOption: MythicPlusRunsSortingOption,
    val selectedSortingOrder: SortingOrder,
    val runs: List<MythicPlusRun>
)
