package com.nemesis.rio.data.profile.character.database.gear

import androidx.room.Entity
import com.nemesis.rio.data.profile.character.database.CharacterPropertyEntity
import com.nemesis.rio.domain.profile.character.gear.Gear

@Entity
class GearEntity(val itemLevel: Int, characterID: Long) : CharacterPropertyEntity(characterID)

internal fun Gear.toEntity(characterID: Long) = GearEntity(itemLevel, characterID)

internal fun GearEntity.toGear() = Gear(itemLevel)
