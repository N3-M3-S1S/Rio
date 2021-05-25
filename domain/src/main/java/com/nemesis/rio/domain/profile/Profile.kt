package com.nemesis.rio.domain.profile

import com.nemesis.rio.domain.server.Region

abstract class Profile {
    abstract val name: String
    abstract val region: Region
    abstract val faction: Faction
    abstract val url: String
}
