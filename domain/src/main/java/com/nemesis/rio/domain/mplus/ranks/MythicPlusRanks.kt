package com.nemesis.rio.domain.mplus.ranks

import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.domain.ranks.Ranks

typealias MythicPlusSpecRanks = Map<Spec, Ranks>

data class MythicPlusClassRanks(
    val ranksForClass: Ranks,
    val ranksForClassRoles: Map<Role, Ranks>,
)

data class MythicPlusOverallRanks(
    val overallRanks: Ranks,
    val overallRoleRanks: Map<Role, Ranks>,
)
