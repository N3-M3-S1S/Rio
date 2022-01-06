package com.nemesis.rio.presentation.profile.overview.character.mplus

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import com.chibatching.kotpref.enumpref.nullableEnumValuePref
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.sorting.SortingOrder
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoresType

object CharacterMythicPlusPreferences : KotprefModel() {
    var scoresType by enumValuePref(MythicPlusScoresType.ROLE_SCORES)
    var ranksType by enumValuePref(MythicPlusRanksType.OVERALL)
    var ranksScope by nullableEnumValuePref(MythicPlusRanksScope.GLOBAL)
    var runsSortingOption by enumValuePref(MythicPlusRunsSortingOption.BY_COMPLETE_DATE)
    var runsSortingOrder by enumValuePref(SortingOrder.DESCENDING)
}
