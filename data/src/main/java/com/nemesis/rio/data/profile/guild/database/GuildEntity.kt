package com.nemesis.rio.data.profile.guild.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.profile.Guild

@Entity(
    tableName = GuildEntity.TABLE_NAME,
    indices = [Index(value = ["name", "region", "realm"], unique = true)]
)
data class GuildEntity(@Embedded val guild: Guild) : ProfileEntity() {
    companion object {
        const val TABLE_NAME = "GuildEntity"
    }
}

internal fun Guild.toEntity() = GuildEntity(this)

internal fun GuildEntity.toGuild() = this.guild
