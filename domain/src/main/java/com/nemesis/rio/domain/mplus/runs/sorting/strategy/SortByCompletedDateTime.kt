package com.nemesis.rio.domain.mplus.runs.sorting.strategy

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import kotlinx.datetime.Instant

object SortByCompletedDateTime : MythicPlusRunsSortingStrategy {

    override fun getComparator() =
        compareBy(Comparator(Instant::compareTo), MythicPlusRun::completedAt)
}
