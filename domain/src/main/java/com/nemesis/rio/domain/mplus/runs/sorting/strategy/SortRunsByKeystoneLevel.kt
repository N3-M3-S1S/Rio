package com.nemesis.rio.domain.mplus.runs.sorting.strategy

import com.nemesis.rio.domain.mplus.runs.MythicPlusRun

object SortRunsByKeystoneLevel : MythicPlusRunsSortingStrategy {

    override fun getComparator() = compareBy(MythicPlusRun::keystoneLevel)
}
