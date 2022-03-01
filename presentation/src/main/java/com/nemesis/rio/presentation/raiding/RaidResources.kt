package com.nemesis.rio.presentation.raiding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.presentation.R

val Raid.stringResId: Int
    @StringRes
    get() = when (this) {
        Raid.THE_EMERALD_NIGHTMARE -> R.string.raid_emerald_nightmare
        Raid.TRIAL_OF_VALOR -> R.string.raid_trial_of_valor
        Raid.THE_NIGHTHOLD -> R.string.raid_nighthold
        Raid.TOMB_OF_SARGERAS -> R.string.raid_tomb_of_sargeras
        Raid.ANTORUS_THE_BURNING_THRONE -> R.string.raid_antorus
        Raid.ULDIR -> R.string.raid_uldir
        Raid.BATTLE_OF_DAZARALOR -> R.string.raid_dazar_alor
        Raid.CRUCIBLE_OF_STORMS -> R.string.raid_crucible_of_storms
        Raid.THE_ETERNAL_PALACE -> R.string.raid_eternal_palace
        Raid.NYALOTHA_THE_WAKING_CITY -> R.string.raid_nyalotha
        Raid.CASTLE_NATHRIA -> R.string.raid_castle_nathria
        Raid.SANCTUM_OF_DOMINATION -> R.string.raid_sanctum_of_domination
        Raid.SEPULCHER_OF_THE_FIRST_ONES -> R.string.raid_sepulcher_of_the_first_ones
    }
val Raid.imageResId
    @DrawableRes
    get() = when (this) {
        Raid.THE_EMERALD_NIGHTMARE -> R.drawable.raid_emerald_nightmare
        Raid.TRIAL_OF_VALOR -> R.drawable.raid_trial_of_valor
        Raid.THE_NIGHTHOLD -> R.drawable.raid_nighthold
        Raid.TOMB_OF_SARGERAS -> R.drawable.raid_tomb_of_sargeras
        Raid.ANTORUS_THE_BURNING_THRONE -> R.drawable.raid_antorus
        Raid.ULDIR -> R.drawable.raid_uldir
        Raid.BATTLE_OF_DAZARALOR -> R.drawable.raid_battle_of_dazaralor
        Raid.CRUCIBLE_OF_STORMS -> R.drawable.raid_crucible_of_storms
        Raid.THE_ETERNAL_PALACE -> R.drawable.raid_eternal_palace
        Raid.NYALOTHA_THE_WAKING_CITY -> R.drawable.raid_nyalotha
        Raid.CASTLE_NATHRIA -> R.drawable.raid_castle_nathria
        Raid.SANCTUM_OF_DOMINATION -> R.drawable.raid_sanctum_of_domination
        Raid.SEPULCHER_OF_THE_FIRST_ONES -> R.drawable.raid_sepulcher_of_the_first_ones
    }
