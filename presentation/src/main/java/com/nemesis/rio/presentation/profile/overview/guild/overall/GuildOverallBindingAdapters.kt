package com.nemesis.rio.presentation.profile.overview.guild.overall

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.ranks.Ranks
import com.nemesis.rio.presentation.raiding.stringResId
import com.nemesis.rio.presentation.ranks.RanksSpan
import splitties.resources.str

@SuppressLint("SetTextI18n")
@BindingAdapter("guildOverall_raid", "guildOverall_difficulty")
fun TextView.setGuildOverallRaidAndDifficulty(raid: Raid?, difficulty: Difficulty?) {
    val raidString = raid?.run { str(stringResId) }
    val difficultyString = difficulty?.run { str(stringResId) }
    text = "$raidString $difficultyString"
}

@BindingAdapter("guildOverall_ranks", "guildOverall_ranksSpan")
fun TextView.setGuildOverallRanks(ranks: Ranks?, span: RanksSpan?) {
    val ranksNotAvailable = "N/A"
    text = if (ranks == null || span == null) ranksNotAvailable else {
        val rank = when (span) {
            RanksSpan.REALM -> ranks.realm
            RanksSpan.REGION -> ranks.region
            RanksSpan.WORLD -> ranks.world
        }
        rank.toString()
    }
}
