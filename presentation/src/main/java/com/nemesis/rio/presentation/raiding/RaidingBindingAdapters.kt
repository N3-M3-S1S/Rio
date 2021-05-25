package com.nemesis.rio.presentation.raiding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.presentation.R
import splitties.resources.color
import splitties.resources.str

@BindingAdapter("raidName")
fun TextView.setRaidName(raid: Raid?) {
    raid?.run { setText(stringResId) }
}

@BindingAdapter("raidAchievementShort")
fun TextView.setRaidAchievementShort(raidAchievement: RaidAchievement?) {
    raidAchievement?.run {
        setText(shortStringResId)
        setTextColor(color(colorResId))
    }
}

@BindingAdapter(
    "raidProgress_bossesKilled",
    "raidProgress_bossesCount",
    "raidProgress_difficulty",
    requireAll = false
)
fun TextView.setRaidProgress(
    bossesKilled: Int,
    bossesCount: Int,
    difficulty: Difficulty? = null
) {
    val raidProgressStringBuilder =
        StringBuilder(str(R.string.raid_progress_kills_total_format, bossesKilled, bossesCount))
    if (difficulty != null) {
        val difficultyNameFirstLetter = str(difficulty.stringResId).first()
        raidProgressStringBuilder.append(" $difficultyNameFirstLetter")
    }
    text = raidProgressStringBuilder.toString()
}
