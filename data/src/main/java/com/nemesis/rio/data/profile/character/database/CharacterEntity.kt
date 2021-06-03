package com.nemesis.rio.data.profile.character.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm

@Entity(
    tableName = CharacterEntity.TABLE_NAME,
    indices = [Index(value = ["name", "region", "realm"], unique = true)]
)
data class CharacterEntity(
    @ColumnInfo(collate = ColumnInfo.NOCASE) val name: String,
    val region: Region,
    val faction: Faction,
    val url: String,
    val realm: Realm,
    val imageUrl: String,
    val guildName: String?
) : ProfileEntity() {
    companion object {
        const val TABLE_NAME = "CharacterEntity"
        const val IMAGE_URL_COLUMN_NAME = "imageUrl"
        const val GUILD_NAME_COLUMN_NAME = "guildName"
    }
}

fun Character.toEntity() = CharacterEntity(name, region, faction, url, realm, imageUrl, guildName)
