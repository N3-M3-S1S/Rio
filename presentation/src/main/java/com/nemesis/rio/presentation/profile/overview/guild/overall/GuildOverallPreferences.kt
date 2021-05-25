package com.nemesis.rio.presentation.profile.overview.guild.overall

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import com.nemesis.rio.presentation.ranks.RanksSpan

object GuildOverallPreferences : KotprefModel() {
    var ranksSpan by enumValuePref(RanksSpan.WORLD)
}
