package com.nemesis.rio.presentation.sorting

import androidx.annotation.StringRes
import com.nemesis.rio.domain.sorting.SortingOrder
import com.nemesis.rio.presentation.R

val SortingOrder.stringResId
    @StringRes
    get() = when (this) {
        SortingOrder.ASC -> R.string.sort_order_asc
        SortingOrder.DESC -> R.string.sort_order_desc
    }
