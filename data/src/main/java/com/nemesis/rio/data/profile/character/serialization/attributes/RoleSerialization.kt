package com.nemesis.rio.data.profile.character.serialization.attributes

import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.utils.enumMapOf

internal object RoleSerialization {
    val roleToJsonValue =
        enumMapOf(Role.DAMAGE_DEALER to "dps", Role.HEALER to "healer", Role.TANK to "tank")
}
