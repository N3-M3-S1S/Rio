package com.nemesis.rio.presentation.server.region

import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.presentation.R

val Region.stringResId: Int
    get() = when (this) {
        Region.EU -> R.string.region_eu
        Region.US -> R.string.region_us
        Region.KR -> R.string.region_kr
        Region.TW -> R.string.region_tw
    }
