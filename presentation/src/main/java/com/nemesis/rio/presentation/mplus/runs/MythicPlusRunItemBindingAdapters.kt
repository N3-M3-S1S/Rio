package com.nemesis.rio.presentation.mplus.runs

import android.view.View
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.databinding.BindingAdapter
import coil.Coil
import coil.request.ImageRequest
import com.nemesis.rio.domain.mplus.Affix
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.mplus.imageResId
import com.nemesis.rio.presentation.mplus.nameResId
import com.nemesis.rio.presentation.utils.toPrettyString
import splitties.resources.str
import splitties.views.setCompoundDrawables
import java.time.Duration

@BindingAdapter("run_dungeonImageBackground")
fun View.setRunDungeonImageBackground(dungeon: Dungeon?) {
    val width = measuredWidth
    val height = measuredHeight
    val isViewMeasured = measuredHeight != 0 && measuredWidth != 0
    if (!isViewMeasured) {
        doOnLayout { setRunDungeonImageBackground(dungeon) }
    } else {
        dungeon?.let {
            ImageRequest.Builder(context)
                .size(width, height)
                .allowRgb565(true)
                .data(it.imageResId)
                .target { dungeonImage -> background = dungeonImage }
                .build()
                .let(Coil::enqueue)
        }
    }
}

@BindingAdapter("run_titleKeystoneLevel", "run_titleDungeon")
fun TextView.setRunTitle(keystoneLevel: Int, dungeon: Dungeon) {
    text = str(
        R.string.mplus_run_keystone_level_dungeon_name_format,
        keystoneLevel,
        str(dungeon.nameResId)
    )
}

@BindingAdapter("run_duration", "run_dungeonDuration")
fun TextView.setRunDuration(runDuration: Duration, dungeonDuration: Duration) {
    val runDurationPrettyString = runDuration.toPrettyString()
    val dungeonDurationPrettyString = dungeonDuration.toPrettyString()
    text = str(
        R.string.mplus_run_duration_dungeon_duration_format,
        runDurationPrettyString,
        dungeonDurationPrettyString
    )
}

@BindingAdapter("run_affixes")
fun MythicPlusRunAffixesView.setRunAffixes(affixes: Map<Affix.Tier, Affix>) {
    this.setAffixes(affixes.values.toList())
}

@BindingAdapter("run_keystoneUpgradesIcon")
fun TextView.setRunKeystoneUpgrades(upgrades: Int) {
    val keystoneUpgradesDrawableId = when (upgrades) {
        0 -> 0
        1 -> R.drawable.item_mplus_run_upgrade_star
        2 -> R.drawable.item_mplus_run_upgrade_two_stars
        3 -> R.drawable.item_mplus_run_upgrade_three_stars
        else -> error("No drawable for upgrades count: $upgrades")
    }
    setCompoundDrawables(end = keystoneUpgradesDrawableId)
}

@BindingAdapter("run_score")
fun TextView.setRunScore(score: MythicPlusScore) {
    text = str(R.string.mplus_run_score_format, score.toPrettyString())
}
