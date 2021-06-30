package com.nemesis.rio.data.mplus.runs.serialization

import com.nemesis.rio.domain.mplus.Affix
import com.nemesis.rio.domain.mplus.Affix.*

internal object AffixSerialization {

    private val Affix.id: Int
        get() = when (this) {
            TYRANNICAL -> 9
            FORTIFIED -> 10
            BOLSTERING -> 7
            RAGING -> 6
            SANGUINE -> 8
            BURSTING -> 11
            INSPIRING -> 122
            SPITEFUL -> 123
            NECROTIC -> 4
            EXPLOSIVE -> 13
            QUAKING -> 14
            VOLCANIC -> 3
            GRIEVOUS -> 12
            STORMING -> 124
            PRIDEFUL -> 121
            TORMENTED -> 128
        }

    fun parseAffixByIdOrNull(affixId: Int): Affix? = enumValues<Affix>().find { affix ->
        affix.id == affixId
    }
}
