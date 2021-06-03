package com.nemesis.rio.domain.profile

import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class Guild(
    name: String,
    region: Region,
    faction: Faction,
    url: String,
    val realm: Realm
) : Profile(name, region, faction, url) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Guild) return false
        if (!super.equals(other)) return false

        if (realm != other.realm) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + realm.hashCode()
        return result
    }

    override fun toString(): String {
        return "Guild(name=$name, region=$region, faction=$faction, url=$url, realm=$realm)"
    }
}
