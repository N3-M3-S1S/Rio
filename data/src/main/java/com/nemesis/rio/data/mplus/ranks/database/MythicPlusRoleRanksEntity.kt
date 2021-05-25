package com.nemesis.rio.data.mplus.ranks.database

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.ranks.Ranks

@Entity(
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class MythicPlusRoleRanksEntity(
    val role: Role,
    ranksType: Int,
    ranks: Ranks,
    scope: MythicPlusRanksScope,
    characterID: Long
) : BaseClassOverallMythicPlusRanksEntity(ranksType, ranks, scope, characterID)

internal fun Map<Role, Ranks>.toRoleRanksEntities(
    ranksType: Int,
    scope: MythicPlusRanksScope,
    characterId: Long,
) = map { (role, ranks) ->
    MythicPlusRoleRanksEntity(
        role,
        ranksType,
        ranks,
        scope,
        characterId
    )
}

internal fun List<MythicPlusRoleRanksEntity>.toRoleRanks() =
    associate { it.role to it.ranks }
