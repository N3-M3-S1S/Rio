package com.nemesis.rio.data.mplus.scores.database

import androidx.room.*
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.domain.profile.character.attributes.Role

@Entity(
    indices = [Index(
        "role",
        "score",
        MythicPlusRoleScoreChildEntity.OVERALL_SCORE_ID_COLUMN_NAME,
        unique = true
    )],

    foreignKeys = [ForeignKey(
        entity = MythicPlusOverallScoreParentEntity::class,
        parentColumns = ["id"],
        childColumns = [MythicPlusRoleScoreChildEntity.OVERALL_SCORE_ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MythicPlusRoleScoreChildEntity(
    val role: Role,
    val score: Float,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = OVERALL_SCORE_ID_COLUMN_NAME)
    var overallScoreID: Long = 0

    companion object {
        const val OVERALL_SCORE_ID_COLUMN_NAME = "overallScoreId"
    }
}

internal fun MythicPlusScores.toRoleScoreEntities() =
    roleScores.map { (role, score) -> MythicPlusRoleScoreChildEntity(role, score) }
