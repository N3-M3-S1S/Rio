package com.nemesis.rio.data.mplus.ranks.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope

@Dao
abstract class MythicPlusRanksDao {

    @Insert
    abstract suspend fun saveMythicPlusRanksEntity(mythicPlusRanksEntity: MythicPlusRanksEntity)

    @Insert
    abstract suspend fun saveMythicPlusSpecRanksEntities(mythicPlusSpecRanksEntity: List<MythicPlusSpecRanksEntity>)

    @Insert
    abstract suspend fun saveMythicPlusRoleRanksEntities(mythicPlusRoleRanksEntities: List<MythicPlusRoleRanksEntity>)

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM (SELECT profileID from MythicPlusRanksEntity UNION ALL SELECT profileID FROM MythicPlusSpecRanksEntity UNION  SELECT profileID from MythicPlusRoleRanksEntity) WHERE profileID = :characterID")
    abstract suspend fun characterHasAnyMythicPlusRanksEntities(characterID: Long): Boolean

    @Query("SELECT * FROM MythicPlusRanksEntity WHERE ranksType =:ranksType AND scope = :ranksScope AND profileID = :characterID")
    abstract suspend fun getMythicPlusRanksEntity(
        ranksType: Int,
        ranksScope: MythicPlusRanksScope,
        characterID: Long
    ): MythicPlusRanksEntity?

    @Query("SELECT * FROM MythicPlusRoleRanksEntity WHERE ranksType =:ranksType AND scope = :ranksScope AND profileID = :characterID")
    abstract suspend fun getMythicPlusRoleRanksEntities(
        ranksType: Int,
        ranksScope: MythicPlusRanksScope,
        characterID: Long
    ): List<MythicPlusRoleRanksEntity>

    @Query("SELECT * FROM MythicPlusSpecRanksEntity WHERE scope = :ranksScope AND profileID = :characterID")
    abstract suspend fun getMythicPlusSpecRanksEntities(
        ranksScope: MythicPlusRanksScope,
        characterID: Long
    ): List<MythicPlusSpecRanksEntity>

    @Transaction
    open suspend fun deleteAll(characterID: Long) {
        deleteMythicPlusRanksEntities(characterID)
        deleteMythicPlusSpecRanksEntities(characterID)
        deleteMythicPlusRoleRanksEntities(characterID)
    }

    @Query("DELETE FROM MythicPlusRanksEntity WHERE profileId = :characterID")
    protected abstract suspend fun deleteMythicPlusRanksEntities(characterID: Long)

    @Query("DELETE FROM MythicPlusSpecRanksEntity WHERE profileId = :characterID")
    protected abstract suspend fun deleteMythicPlusSpecRanksEntities(characterID: Long)

    @Query("DELETE FROM MythicPlusRoleRanksEntity WHERE profileId = :characterID")
    protected abstract suspend fun deleteMythicPlusRoleRanksEntities(characterID: Long)
}
