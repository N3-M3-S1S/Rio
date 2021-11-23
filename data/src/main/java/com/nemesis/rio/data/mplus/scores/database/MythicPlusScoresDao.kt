package com.nemesis.rio.data.mplus.scores.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.seasons.Season

@Dao
abstract class MythicPlusScoresDao {

    @Insert
    internal abstract suspend fun saveOverallScores(overallScoreEntities: List<MythicPlusOverallScoreEntity>)

    @Insert
    internal abstract suspend fun saveRoleScoreEntities(roleScoreEntities: List<MythicPlusRoleScoreEntity>)

    @Insert
    internal abstract suspend fun saveSpecScoreEntities(specScoreEntities: List<MythicPlusSpecScoreEntity>)

    @Query("SELECT score FROM MythicPlusOverallScoreEntity WHERE characterId = :characterId AND seasonId = (SELECT id from SEASONS where name = :season)")
    internal abstract suspend fun getOverallScore(
        characterId: Long,
        season: Season
    ): MythicPlusScore?

    @Query("SELECT * FROM MythicPlusRoleScoreEntity WHERE characterId = :characterId AND seasonId = (SELECT id from SEASONS where name = :season)")
    internal abstract suspend fun getRoleScoreEntities(
        characterId: Long,
        season: Season
    ): List<MythicPlusRoleScoreEntity>

    @Query("SELECT * FROM MythicPlusSpecScoreEntity WHERE characterId = :characterId AND seasonId = (SELECT id FROM SEASONS WHERE name = :season)")
    internal abstract suspend fun getSpecScoreEntities(characterId: Long, season: Season): List<MythicPlusSpecScoreEntity>

    @Transaction
    internal open suspend fun deleteAllScores(characterId: Long) {
        deleteOverallScores(characterId)
        deleteRoleScores(characterId)
        deleteSpecScores(characterId)
    }

    @Query("DELETE FROM MythicPlusOverallScoreEntity WHERE characterId = :characterId")
    protected abstract suspend fun deleteOverallScores(characterId: Long)

    @Query("DELETE FROM MythicPlusRoleScoreEntity WHERE characterId = :characterId")
    protected abstract suspend fun deleteRoleScores(characterId: Long)

    @Query("DELETE FROM MythicPlusSpecScoreEntity WHERE characterId = :characterId")
    protected abstract suspend fun deleteSpecScores(characterId: Long)

    @Query("SELECT DISTINCT expansion from seasons WHERE id in (SELECT seasonId from MythicPlusOverallScoreEntity where characterId = :characterID) ORDER BY expansion DESC")
    internal abstract suspend fun getExpansionsWithScores(characterID: Long): List<Expansion>

    @Query("SELECT name from seasons where expansion = :expansion and id in (SELECT seasonId from MythicPlusOverallScoreEntity where characterId = :characterID) ORDER BY id DESC")
    internal abstract suspend fun getSeasonsWithScores(
        characterID: Long,
        expansion: Expansion
    ): List<Season>
}
