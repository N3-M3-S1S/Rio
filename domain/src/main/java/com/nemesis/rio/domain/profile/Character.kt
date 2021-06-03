package com.nemesis.rio.domain.profile

import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes
import com.nemesis.rio.domain.profile.character.gear.Gear
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

class Character(
    name: String,
    region: Region,
    faction: Faction,
    url: String,
    val realm: Realm,
    val attributes: CharacterAttributes,
    val gear: Gear,
    val imageUrl: String,
    val guildName: String?,
) : Profile(name, region, faction, url) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Character) return false
        if (!super.equals(other)) return false

        if (realm != other.realm) return false
        if (attributes != other.attributes) return false
        if (gear != other.gear) return false
        if (imageUrl != other.imageUrl) return false
        if (guildName != other.guildName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + realm.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + gear.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + (guildName?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Character(name='$name', region=$region, faction=$faction, url='$url', realm='$realm', " +
                "attributes=$attributes, gear=$gear, imageUrl='$imageUrl', guildName=$guildName)}"
    }
}
