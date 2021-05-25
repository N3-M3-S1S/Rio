package com.nemesis.rio.presentation.mplus.ranks

import androidx.annotation.StringRes
import com.nemesis.rio.presentation.R

enum class MythicPlusRanksType {
    OVERALL, SPEC, CLASS,
}

val MythicPlusRanksType.stringResID: Int
    @StringRes
    get() = when (this) {
        MythicPlusRanksType.OVERALL -> R.string.character_mplus_ranks_overall
        MythicPlusRanksType.SPEC -> R.string.character_mplus_ranks_spec
        MythicPlusRanksType.CLASS -> R.string.character_mplus_ranks_class
    }
