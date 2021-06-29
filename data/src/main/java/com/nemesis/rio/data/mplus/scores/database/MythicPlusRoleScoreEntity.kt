package com.nemesis.rio.data.mplus.scores.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.profile.character.attributes.Role

@Entity(
    tableName = MythicPlusRoleScoreEntity.TABLE_NAME,
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
class MythicPlusRoleScoreEntity(
    @ColumnInfo(name = ROLE_COLUMN)
    val role: Role,
    score: MythicPlusScore,
    seasonId: Int,
    characterId: Long
) : MythicPlusScoreBaseEntity(score, seasonId, characterId) {

    companion object {
        const val TABLE_NAME = "MythicPlusRoleScoreEntity"
        const val ROLE_COLUMN = "role"
    }
}

internal fun List<MythicPlusRoleScoreEntity>.toRoleScores() = associate { it.role to it.score }

