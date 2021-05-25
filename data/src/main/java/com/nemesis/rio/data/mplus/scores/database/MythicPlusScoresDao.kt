package com.nemesis.rio.data.mplus.scores.database

import androidx.room.*
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season

@Dao
abstract class MythicPlusScoresDao {

    @Transaction
    open suspend fun saveOrUpdate(scoresPojos: List<MythicPlusScoresPojo>) {
        scoresPojos.forEach { mythicPlusScoresPojo ->
            with(mythicPlusScoresPojo) {
                val overallScoreID = saveOrUpdateParentScoresEntity(parentScoresEntity)
                roleScoreEntities
                    .onEach { it.overallScoreID = overallScoreID }
                    .let { saveOrUpdateRoleScoreEntities(it) }
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun saveOrUpdateParentScoresEntity(entity: MythicPlusOverallScoreParentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun saveOrUpdateRoleScoreEntities(entities: List<MythicPlusRoleScoreChildEntity>)

    @Query("SELECT * FROM MythicPlusOverallScoreParentEntity WHERE profileId = :characterID AND seasonID = (SELECT id from seasons WHERE name = :season)")
    abstract suspend fun getScoresPojoForSeason(
        season: Season,
        characterID: Long,
    ): MythicPlusScoresPojo?

    @Query("SELECT DISTINCT expansion from seasons WHERE id in (SELECT seasonID from MythicPlusOverallScoreParentEntity where profileID = :characterID) ORDER BY expansion DESC")
    abstract suspend fun getExpansionsWithScores(characterID: Long): List<Expansion>

    @Query("SELECT name from seasons where expansion = :expansion and id in (SELECT seasonID from MythicPlusOverallScoreParentEntity where profileID = :characterID) ORDER BY id DESC")
    abstract suspend fun getSeasonsWithScores(characterID: Long, expansion: Expansion): List<Season>
}
