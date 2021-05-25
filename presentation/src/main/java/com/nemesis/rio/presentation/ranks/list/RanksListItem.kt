package com.nemesis.rio.presentation.ranks.list

import androidx.annotation.DrawableRes
import com.nemesis.rio.domain.ranks.Ranks

data class RanksListItem(
    val title: String,
    val ranks: Ranks,
    @DrawableRes val iconID: Int? = null,
)
