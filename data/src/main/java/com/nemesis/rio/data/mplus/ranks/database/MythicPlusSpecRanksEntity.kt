package com.nemesis.rio.data.mplus.ranks.database

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.MythicPlusSpecRanks
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.domain.ranks.Ranks

@Entity(
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class MythicPlusSpecRanksEntity(
    val spec: Spec,
    ranks: Ranks,
    scope: MythicPlusRanksScope,
    characterID: Long
) : BaseMythicPlusRanksEntity(ranks, scope, characterID)

internal fun MythicPlusSpecRanks.toSpecRanksEntities(
    scope: MythicPlusRanksScope,
    characterId: Long
) =
    map { (spec, ranks) ->
        MythicPlusSpecRanksEntity(
            spec,
            ranks,
            scope,
            characterId
        )
    }

internal fun List<MythicPlusSpecRanksEntity>.toSpecRanks() = associate { it.spec to it.ranks }
