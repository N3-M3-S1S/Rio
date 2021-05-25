package com.nemesis.rio.data.mplus.runs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class MythicPlusRunsDao {

    @Transaction
    open suspend fun saveMythicPlusRunPojos(runPojos: List<MythicPlusRunPojo>) {
        runPojos.forEach { pojo ->
            with(pojo) {
                val runId = saveRunEntity(runEntity)
                affixesEntity.runID = runId
                saveAffixesEntity(affixesEntity)
            }
        }
    }

    @Insert
    protected abstract suspend fun saveRunEntity(entity: MythicPlusRunEntity): Long

    @Insert
    protected abstract suspend fun saveAffixesEntity(entity: MythicPlusRunAffixesEntity)

    @Query("DELETE FROM MythicPlusRunEntity WHERE profileId = :characterID")
    abstract suspend fun deleteAll(characterID: Long)

    @Transaction
    @Query("SELECT * FROM MythicPlusRunEntity WHERE profileId = :characterID")
    abstract suspend fun getMythicPlusRunsPojos(characterID: Long): List<MythicPlusRunPojo>
}
