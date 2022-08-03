package com.nemesis.rio.presentation.mplus

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.presentation.R

val Dungeon.nameResId: Int
    @StringRes
    get() = when (this) {
        Dungeon.GRIMRAIL_DEPOT -> R.string.dungeon_grimrail_depot
        Dungeon.IRON_DOCKS -> R.string.dungeon_iron_docks
        Dungeon.MECHAGON_JUNKYARD -> R.string.dungeon_mechagon_junkyard
        Dungeon.MECHAGON_WORKSHOP -> R.string.dungeon_mechagon_workshop
        Dungeon.KARAZHAN_LOWER -> R.string.dungeon_karazhan_lower
        Dungeon.KARAZHAN_UPPER -> R.string.dungeon_karazhan_upper
        Dungeon.TAZAVESH_GMBT -> R.string.dungeon_tazavesh_gmbt
        Dungeon.TAZAVESH_STRT -> R.string.dungeon_tazavesh_strt
    }

val Dungeon.imageResId: Int
    @DrawableRes
    get() {
        return when (this) {
            Dungeon.GRIMRAIL_DEPOT -> R.drawable.dungeon_grimrail_depot
            Dungeon.IRON_DOCKS -> R.drawable.dungeon_iron_docks
            Dungeon.MECHAGON_JUNKYARD, Dungeon.MECHAGON_WORKSHOP -> R.drawable.dungeon_mechagon
            Dungeon.KARAZHAN_LOWER, Dungeon.KARAZHAN_UPPER -> R.drawable.dungeon_karazhan
            Dungeon.TAZAVESH_STRT, Dungeon.TAZAVESH_GMBT -> R.drawable.dungeon_tazavesh
        }
    }
