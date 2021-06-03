package com.nemesis.rio.data.profile.character.database

import androidx.room.Embedded
import androidx.room.Relation
import com.nemesis.rio.data.profile.character.database.attributes.CharacterAttributesEntity
import com.nemesis.rio.data.profile.character.database.attributes.toAttributes
import com.nemesis.rio.data.profile.character.database.gear.GearEntity
import com.nemesis.rio.data.profile.character.database.gear.toGear
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.profile.Character

data class CharacterPojo(
    @Embedded
    val characterEntity: CharacterEntity,

    @Relation(
        parentColumn = ProfileEntity.ID_COLUMN_NAME,
        entityColumn = ProfileEntity.ID_COLUMN_NAME
    )
    val attributesEntity: CharacterAttributesEntity,

    @Relation(
        parentColumn = ProfileEntity.ID_COLUMN_NAME,
        entityColumn = ProfileEntity.ID_COLUMN_NAME
    )
    val gearEntity: GearEntity,
)

fun CharacterPojo.toCharacter(): Character {
    val attributes = attributesEntity.toAttributes()
    val gear = gearEntity.toGear()
    return with(characterEntity) {
        Character(name, region, faction, url, realm, attributes, gear, imageUrl, guildName)
    }
}
