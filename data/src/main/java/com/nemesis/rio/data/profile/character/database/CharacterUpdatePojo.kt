package com.nemesis.rio.data.profile.character.database

import androidx.room.ColumnInfo
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.profile.Character

data class CharacterUpdatePojo(
    @ColumnInfo(name = ProfileEntity.ID_COLUMN_NAME)
    val id: Long,
    @ColumnInfo(name = CharacterEntity.IMAGE_URL_COLUMN_NAME)
    val imageUrl: String,
    @ColumnInfo(name = CharacterEntity.GUILD_NAME_COLUMN_NAME)
    val guildName: String?
)

internal fun Character.toUpdatePojo(characterID: Long) =
    CharacterUpdatePojo(characterID, imageUrl, guildName)
