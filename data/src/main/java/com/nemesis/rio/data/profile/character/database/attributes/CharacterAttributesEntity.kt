package com.nemesis.rio.data.profile.character.database.attributes

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.character.database.CharacterPropertyEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.profile.character.attributes.CharacterAttributes

@Entity(
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class CharacterAttributesEntity(@Embedded var attributes: CharacterAttributes, characterID: Long) :
    CharacterPropertyEntity(characterID)

internal fun CharacterAttributes.toEntity(characterID: Long) =
    CharacterAttributesEntity(this, characterID)

internal fun CharacterAttributesEntity.toAttributes() = attributes
