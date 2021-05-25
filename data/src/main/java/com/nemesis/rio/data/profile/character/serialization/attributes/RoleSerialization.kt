package com.nemesis.rio.data.profile.character.serialization.attributes

import androidx.collection.arrayMapOf
import com.nemesis.rio.domain.profile.character.attributes.Role

internal object RoleSerialization {
    val jsonValueToRole =
        arrayMapOf("dps" to Role.DAMAGE_DEALER, "healer" to Role.HEALER, "tank" to Role.TANK)
}
