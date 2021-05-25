package com.nemesis.rio.domain.mplus.runs.sorting.strategy

import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun

class SortRunsByDungeonName(private val dungeonToLocalizedName: Map<Dungeon, String>) :
    MythicPlusRunsSortingStrategy {

    override fun getComparator(): Comparator<MythicPlusRun> {
        return compareBy(String.CASE_INSENSITIVE_ORDER) { dungeonToLocalizedName.getValue(it.dungeon) }
    }
}
