package com.nemesis.rio.domain.mplus.runs.sorting.strategy

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import kotlinx.datetime.LocalDateTime

object SortRunsByCompleteDate : MythicPlusRunsSortingStrategy {

    override fun getComparator() =
        compareBy(Comparator(LocalDateTime::compareTo), MythicPlusRun::date)
}
