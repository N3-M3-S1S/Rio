package com.nemesis.rio.domain.mplus.runs.usecase

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.mplus.runs.sorting.strategy.MythicPlusRunsSortingStrategy
import com.nemesis.rio.domain.sorting.SortingOrder

class SortMythicPlusRuns(private val sortStrategies: Map<MythicPlusRunsSortingOption, MythicPlusRunsSortingStrategy>) {

    operator fun invoke(
        sortBy: MythicPlusRunsSortingOption,
        sortingOrder: SortingOrder,
        runs: List<MythicPlusRun>,
    ): List<MythicPlusRun> {
        if (runs.size < 2) return runs
        val comparator = sortStrategies.getValue(sortBy).getComparator()
        val comparatorForOrder =
            if (sortingOrder == SortingOrder.DESC) compareByDescending(comparator) { it } else comparator
        return runs.sortedWith(comparatorForOrder)
    }
}
