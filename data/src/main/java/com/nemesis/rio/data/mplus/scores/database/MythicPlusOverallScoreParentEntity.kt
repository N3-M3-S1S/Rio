package com.nemesis.rio.data.mplus.scores.database

import androidx.room.*
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.data.progress.database.CharacterProgressEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores

@Entity(
    indices = [Index(
        MythicPlusOverallScoreParentEntity.SEASON_ID_COLUMN_NAME,
        ProfileEntity.ID_COLUMN_NAME,
        unique = true
    )],

    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class MythicPlusOverallScoreParentEntity(
    val overallScore: Float,
    @ColumnInfo(name = SEASON_ID_COLUMN_NAME) val seasonID: Long,
    characterID: Long,
) : CharacterProgressEntity(characterID) {

    companion object {
        const val SEASON_ID_COLUMN_NAME = "seasonId"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

internal fun MythicPlusScores.toParentScoresEntity(seasonID: Long, characterID: Long) =
    MythicPlusOverallScoreParentEntity(overallScore, seasonID, characterID)
