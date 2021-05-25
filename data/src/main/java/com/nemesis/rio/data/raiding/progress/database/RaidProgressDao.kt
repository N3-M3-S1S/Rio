package com.nemesis.rio.data.raiding.progress.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nemesis.rio.domain.raiding.Raid

@Dao
interface RaidProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(entities: List<RaidProgressEntity>)

    @Query("SELECT * FROM RaidProgressEntity WHERE raid = :raid AND profileID = :profileID AND profileType = :profileType")
    suspend fun getRaidProgressEntity(
        raid: Raid,
        profileID: Long,
        profileType: Int
    ): RaidProgressEntity?

    @Query("SELECT * FROM RaidProgressEntity WHERE profileId = :profileId AND profileType = :profileType")
    suspend fun getAllRaidProgressEntities(
        profileId: Long,
        profileType: Int
    ): List<RaidProgressEntity>

    @Query("DELETE FROM RaidProgressEntity WHERE raid NOT IN (:raidsToKeep) AND profileId = :profileId AND profileType = :profileType")
    suspend fun deleteProgressIfRaidIsNotInList(
        raidsToKeep: List<Raid>,
        profileId: Long,
        profileType: Int
    )

    @Query("DELETE FROM RaidProgressEntity WHERE profileID = :profileID AND profileType = :profileType")
    suspend fun deleteAllProgress(profileID: Long, profileType: Int)
}
