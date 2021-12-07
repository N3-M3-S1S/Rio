package com.nemesis.rio.data.mplus.scores.database

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.profile.character.attributes.Spec

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = [ProfileEntity.ID_COLUMN_NAME],
            childColumns = [MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SeasonEntity::class,
            parentColumns = ["id"],
            childColumns = [MythicPlusScoreBaseEntity.SEASON_ID_COLUMN]
        )
    ]
)
class MythicPlusSpecScoreEntity(
    val spec: Spec,
    score: MythicPlusScore,
    seasonId: Int,
    characterId: Long
) : MythicPlusScoreBaseEntity(score, seasonId, characterId)

internal fun List<MythicPlusSpecScoreEntity>.toSpecScores() = associate { it.spec to it.score }
