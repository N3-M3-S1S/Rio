package com.nemesis.rio.data.mplus.scores.database

import androidx.room.Embedded
import androidx.room.Relation
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.domain.mplus.seasons.Season

data class MythicPlusScoresPojo(
    @Embedded
    val parentScoresEntity: MythicPlusOverallScoreParentEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = MythicPlusRoleScoreChildEntity.OVERALL_SCORE_ID_COLUMN_NAME,
    )
    val roleScoreEntities: List<MythicPlusRoleScoreChildEntity>,
) {

    @Relation(
        entity = SeasonEntity::class,
        parentColumn = MythicPlusOverallScoreParentEntity.SEASON_ID_COLUMN_NAME,
        entityColumn = "id",
        projection = ["name"]
    )
    lateinit var season: Season
}

internal fun Map<Long, MythicPlusScores>.toScoresPojos(characterID: Long) =
    map { (seasonID, scores) -> scores.toScoresPojo(seasonID, characterID) }

private fun MythicPlusScores.toScoresPojo(
    seasonID: Long,
    characterID: Long,
): MythicPlusScoresPojo {
    val parentScoresEntity = this.toParentScoresEntity(seasonID, characterID)
    val roleScoresEntities = this.toRoleScoreEntities()
    return MythicPlusScoresPojo(parentScoresEntity, roleScoresEntities)
}

internal fun MythicPlusScoresPojo.toMythicPlusScores() =
    MythicPlusScores(
        parentScoresEntity.overallScore,
        roleScoreEntities.sortedByDescending { it.score }.associate { it.role to it.score })
