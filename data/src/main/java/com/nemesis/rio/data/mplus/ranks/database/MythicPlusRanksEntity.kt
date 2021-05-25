package com.nemesis.rio.data.mplus.ranks.database

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.ranks.Ranks

@Entity(
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class MythicPlusRanksEntity(
    ranksType: Int,
    ranks: Ranks,
    scope: MythicPlusRanksScope,
    characterID: Long
) : BaseClassOverallMythicPlusRanksEntity(ranksType, ranks, scope, characterID)

internal fun Ranks.toRanksEntity(
    ranksType: Int,
    scope: MythicPlusRanksScope,
    characterId: Long,
) = MythicPlusRanksEntity(
    ranksType,
    this,
    scope,
    characterId
)
