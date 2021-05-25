package com.nemesis.rio.data.profile.guild.api

import com.nemesis.rio.data.profile.api.ProfileSearchFields
import com.nemesis.rio.data.raiding.serialization.RaidSerialization
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.utils.joinStrings

@JvmInline
value class GuildSearchFields private constructor(private val apiFieldsQueryParameter: String) {

    internal companion object {
        const val RAID_RANKINGS = "raid_rankings"
    }

    class Factory {
        private var guildSearchFields: GuildSearchFields? = null

        fun create(): GuildSearchFields {
            if (guildSearchFields == null) {
                guildSearchFields = GuildSearchFields(buildApiFieldsQueryParameter())
            }
            return guildSearchFields!!
        }

        private fun buildApiFieldsQueryParameter(): String = joinStrings {
            val raidsJsonValuesString = enumValues<Raid>().joinToString(
                separator = ":",
                prefix = ":",
                transform = RaidSerialization::getRaidJsonValue
            )
            add(ProfileSearchFields.RAID_PROGRESSION + raidsJsonValuesString)
            add(RAID_RANKINGS + raidsJsonValuesString)
        }
    }
}
