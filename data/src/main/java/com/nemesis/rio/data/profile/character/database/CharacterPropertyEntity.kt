package com.nemesis.rio.data.profile.character.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.nemesis.rio.data.profile.database.ProfileEntity

abstract class CharacterPropertyEntity(
    @ColumnInfo(name = ProfileEntity.ID_COLUMN_NAME) @PrimaryKey var characterID: Long
)
