package com.nemesis.rio.presentation.mplus

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.presentation.R

val Dungeon.nameResId: Int
    @StringRes
    get() = when (this) {
        Dungeon.DE_OTHER_SIDE -> R.string.dungeon_de_other_side
        Dungeon.MISTS_OF_THIRNA_SCITHE -> R.string.dungeon_mists_of_tirna_scithe
        Dungeon.SANGUINE_DEPTHS -> R.string.dungeon_sanguine_depths
        Dungeon.HALLS_OF_ATONEMENT -> R.string.dungeon_halls_of_atonement
        Dungeon.NECROTIC_WAKE -> R.string.dungeon_necrotic_wake
        Dungeon.SPIRES_OF_ASCENSION -> R.string.dungeon_spires_of_ascension
        Dungeon.THEATER_OF_PAIN -> R.string.dungeon_theater_of_pain
        Dungeon.PLAGUEFALL -> R.string.dungeon_plaguefall
        Dungeon.TAZAVESH_STRT -> R.string.dungeon_tazavesh_strt
        Dungeon.TAZAVESH_GMBT -> R.string.dungeon_tazavesh_gmbt
    }

val Dungeon.imageResId: Int
    @DrawableRes
    get() {
        return when (this) {
            Dungeon.DE_OTHER_SIDE -> R.drawable.dungeon_de_other_side
            Dungeon.MISTS_OF_THIRNA_SCITHE -> R.drawable.dungeon_mists_of_tirna_scithe
            Dungeon.SANGUINE_DEPTHS -> R.drawable.dungeon_sanguine_depths
            Dungeon.HALLS_OF_ATONEMENT -> R.drawable.dungeon_halls_of_atonment
            Dungeon.NECROTIC_WAKE -> R.drawable.dungeon_necrotic_wake
            Dungeon.SPIRES_OF_ASCENSION -> R.drawable.dungeon_spires_of_ascension
            Dungeon.THEATER_OF_PAIN -> R.drawable.dungeon_theater_of_pain
            Dungeon.PLAGUEFALL -> R.drawable.dungeon_plaguefall
            Dungeon.TAZAVESH_STRT, Dungeon.TAZAVESH_GMBT -> R.drawable.dungeon_tazavesh
        }
    }
