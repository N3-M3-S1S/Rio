package com.nemesis.rio.presentation.mplus.scores

import androidx.annotation.StringRes
import com.airbnb.epoxy.IdUtils
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.character.attributes.nameResId

data class MythicPlusScoreItem(val id: Long, @StringRes val titleResId: Int, val score: Float)

@OptIn(ExperimentalStdlibApi::class)
fun MythicPlusScores.toScoreItems() = buildList<MythicPlusScoreItem> {
    roleScores.forEach { (role, score) ->
        add(MythicPlusScoreItem(IdUtils.hashString64Bit(role.name), role.nameResId, score))
    }

    if (size > 1) {
        val isOverallScoreHigherThanHighestRoleScore = overallScore > this.first().score
        if (isOverallScoreHigherThanHighestRoleScore) {
            add(
                MythicPlusScoreItem(
                    IdUtils.hashString64Bit("overall"),
                    R.string.character_mplus_score_overall,
                    overallScore
                )
            )
        }
        sortByDescending { it.score }
    }
}
