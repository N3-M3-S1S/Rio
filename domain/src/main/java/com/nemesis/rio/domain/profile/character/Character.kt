package com.nemesis.rio.domain.profile.character

import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes
import com.nemesis.rio.domain.profile.character.gear.Gear
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

data class Character(
    override val name: String,
    override val region: Region,
    override val faction: Faction,
    override val url: String,
    val realm: Realm,
    val attributes: CharacterAttributes,
    val gear: Gear,
    val imageUrl: String,
    val guildName: String?,
) : Profile()
