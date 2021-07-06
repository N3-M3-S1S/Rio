package com.nemesis.rio.presentation.utils

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import timber.log.Timber
import java.time.Duration
import java.util.*

fun MythicPlusScore.toPrettyString(): String {
    val scoreHasDecimalPart = this % 1f != 0f
    return if (scoreHasDecimalPart) {
        String.format(Locale.getDefault(), "%.1f", this)
    } else {
        this.toInt().toString()
    }
}

fun Duration.toPrettyString(): String {
    val hours = toHours()
    val minutes = minusHours(hours).toMinutes()
    val seconds = minusHours(hours).minusMinutes(minutes).seconds

    return buildString {
        if (hours > 0) {
            append(hours.toString())
            append(":")
        }
        if (minutes < 10) {
            append("0")
        }
        append(minutes.toString())
        append(":")
        if (seconds < 10) {
            append("0")
        }
        append(seconds.toString())
    }
}
