package com.nemesis.rio.data.profile.serialization

import com.nemesis.rio.data.profile.api.ProfileSearchParameters
import com.nemesis.rio.data.serialization.getUnquotedString
import com.nemesis.rio.data.server.api.ServerParameters
import com.nemesis.rio.data.server.serialization.RegionSerialization
import com.nemesis.rio.domain.profile.Faction
import kotlinx.serialization.json.JsonObject

internal object ProfileSerialization {
    fun parseName(profileJsonObject: JsonObject) =
        profileJsonObject.getUnquotedString(ProfileSearchParameters.NAME)

    fun parseFaction(profileJsonObject: JsonObject): Faction {
        return when (val factionJsonValue = profileJsonObject.getUnquotedString("faction")) {
            "horde" -> Faction.HORDE
            "alliance" -> Faction.ALLIANCE
            else -> error("Unknown faction json value: $factionJsonValue")
        }
    }

    fun parseRegion(profileJsonObject: JsonObject) =
        RegionSerialization.getRegionFromJsonValue(profileJsonObject.getUnquotedString(ServerParameters.REGION))

    fun parseUrl(profileJsonObject: JsonObject) = profileJsonObject.getUnquotedString("profile_url")

    fun parseRealm(profileJsonObject: JsonObject) =
        profileJsonObject.getUnquotedString(ServerParameters.REALM)
}
