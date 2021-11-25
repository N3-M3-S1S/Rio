package com.nemesis.rio.presentation.mplus.scores

import androidx.annotation.StringRes
import com.nemesis.rio.presentation.R

enum class MythicPlusScoresType {
    ROLE_SCORES, SPEC_SCORES
}

val MythicPlusScoresType.stringResId: Int
    @StringRes
    get() = when (this) {
        MythicPlusScoresType.ROLE_SCORES -> R.string.character_mplus_scores_role
        MythicPlusScoresType.SPEC_SCORES -> R.string.character_mpls_scores_spec
    }
