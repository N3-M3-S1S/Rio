package com.nemesis.rio.domain.profile

import com.nemesis.rio.domain.server.Region

sealed class Profile(
    val name: String,
    val region: Region,
    val faction: Faction,
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Profile) return false

        if (name != other.name) return false
        if (region != other.region) return false
        if (faction != other.faction) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + region.hashCode()
        result = 31 * result + faction.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }

    override fun toString(): String {
        return "Profile(name='$name', region=$region, faction=$faction, url='$url')"
    }
}
