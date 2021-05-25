package com.nemesis.rio.presentation.utils.extensions

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

private const val MISSING_VALUE = -1

fun TypedArray.getIntOrNull(@StyleableRes index: Int) =
    getInt(index, MISSING_VALUE).takeIf { it != MISSING_VALUE }
