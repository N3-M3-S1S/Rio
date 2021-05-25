package com.nemesis.rio.domain.profile.guild

import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

data class Guild(
    override val name: String,
    override val region: Region,
    override val faction: Faction,
    override val url: String,
    val realm: Realm
) : Profile()
