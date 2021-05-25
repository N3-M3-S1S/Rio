package com.nemesis.rio.presentation.mplus.runs

import androidx.annotation.StringRes
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.presentation.R

val MythicPlusRunsSortingOption.stringResId: Int
    @StringRes
    get() = when (this) {
        MythicPlusRunsSortingOption.BY_DUNGEON_NAME -> R.string.mplus_runs_sort_by_dungeon_name
        MythicPlusRunsSortingOption.BY_KEYSTONE_LEVEL -> R.string.mplus_runs_sort_by_keystone_level
        MythicPlusRunsSortingOption.BY_COMPLETE_DATE -> R.string.mplus_runs_sort_by_complete_date
        MythicPlusRunsSortingOption.BY_KEYSTONE_UPGRADES -> R.string.mplus_runs_sort_by_keystone_upgrades
        MythicPlusRunsSortingOption.BY_SCORE -> R.string.mplus_runs_sort_by_score
    }
