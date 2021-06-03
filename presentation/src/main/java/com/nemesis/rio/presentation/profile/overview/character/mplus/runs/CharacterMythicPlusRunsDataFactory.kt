package com.nemesis.rio.presentation.profile.overview.character.mplus.runs

import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.mplus.runs.usecase.GetMythicPlusRunsForCurrentSeason
import com.nemesis.rio.domain.mplus.runs.usecase.SortMythicPlusRuns
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.sorting.SortingOrder

class CharacterMythicPlusRunsDataFactory(
    private val getMythicPlusRuns: GetMythicPlusRunsForCurrentSeason,
    private val sortMythicPlusRuns: SortMythicPlusRuns
) {

    suspend fun getMythicPlusRunsDataForCurrentSeason(
        character: Character,
        sortingOption: MythicPlusRunsSortingOption,
        sortingOrder: SortingOrder
    ): CharacterMythicPlusRunsData? {
        val runs = sortMythicPlusRuns(sortingOption, sortingOrder, getMythicPlusRuns(character))
        return if (runs.isEmpty()) {
            null
        } else {
            CharacterMythicPlusRunsData(sortingOption, sortingOrder, runs)
        }
    }

    fun updateSortingOption(
        sortingOption: MythicPlusRunsSortingOption,
        oldData: CharacterMythicPlusRunsData
    ): CharacterMythicPlusRunsData {
        val runs = sortMythicPlusRuns(sortingOption, oldData.selectedSortingOrder, oldData.runs)
        return oldData.copy(selectedSortingOption = sortingOption, runs = runs)
    }

    fun updateSortingOrder(
        sortingOrder: SortingOrder,
        oldData: CharacterMythicPlusRunsData
    ): CharacterMythicPlusRunsData {
        val runs = sortMythicPlusRuns(oldData.selectedSortingOption, sortingOrder, oldData.runs)
        return oldData.copy(selectedSortingOrder = sortingOrder, runs = runs)
    }
}
