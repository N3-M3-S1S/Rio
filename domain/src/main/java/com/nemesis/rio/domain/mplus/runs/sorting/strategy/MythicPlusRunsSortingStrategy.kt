package com.nemesis.rio.domain.mplus.runs.sorting.strategy

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun

interface MythicPlusRunsSortingStrategy {
    fun getComparator(): Comparator<MythicPlusRun>
}
