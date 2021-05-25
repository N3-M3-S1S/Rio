package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.Covenant

internal object CovenantSerialization {

    private val Covenant.id
        get() = when (this) {
            Covenant.KYRIAN -> 1
            Covenant.NECROLORD -> 4
            Covenant.NIGHT_FAE -> 3
            Covenant.VENTHYR -> 2
        }

    fun parseCovenantById(covenantId: Int): Covenant =
        enumValues<Covenant>().first { covenant -> covenant.id == covenantId }
}
