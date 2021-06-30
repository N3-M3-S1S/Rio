package com.nemesis.rio.presentation.mplus

import androidx.annotation.DrawableRes
import com.nemesis.rio.domain.mplus.Affix
import com.nemesis.rio.presentation.R

val Affix.imageResId
    @DrawableRes
    get() = when (this) {
        Affix.TYRANNICAL -> R.drawable.affix_tyrranical
        Affix.FORTIFIED -> R.drawable.affix_fortified
        Affix.BOLSTERING -> R.drawable.affix_bolstering
        Affix.RAGING -> R.drawable.affix_raging
        Affix.SANGUINE -> R.drawable.affix_sanguine
        Affix.BURSTING -> R.drawable.affix_bursting
        Affix.INSPIRING -> R.drawable.affix_inspiring
        Affix.SPITEFUL -> R.drawable.affix_spiteful
        Affix.NECROTIC -> R.drawable.affix_nectoric
        Affix.EXPLOSIVE -> R.drawable.affix_explosive
        Affix.QUAKING -> R.drawable.affix_quaking
        Affix.VOLCANIC -> R.drawable.affix_volcanic
        Affix.GRIEVOUS -> R.drawable.affix_griveous
        Affix.STORMING -> R.drawable.affix_storming
        Affix.PRIDEFUL -> R.drawable.affix_prideful
        Affix.TORMENTED -> R.drawable.affix_tormented
    }
