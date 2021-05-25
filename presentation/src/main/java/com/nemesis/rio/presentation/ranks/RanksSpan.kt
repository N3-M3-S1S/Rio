package com.nemesis.rio.presentation.ranks

import androidx.annotation.StringRes
import com.nemesis.rio.presentation.R

enum class RanksSpan {
    REALM, REGION, WORLD
}

val RanksSpan.stringResId: Int
    @StringRes
    get() = when (this) {
        RanksSpan.REALM -> R.string.realm_title
        RanksSpan.REGION -> R.string.region_title
        RanksSpan.WORLD -> R.string.ranks_scope_world
    }
