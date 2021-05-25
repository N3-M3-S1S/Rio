package com.nemesis.rio.data.raiding.achievements.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nemesis.rio.domain.raiding.Raid

@Dao
interface RaidAchievementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(entities: List<RaidAchievementsEntity>)

    @Query("SELECT * FROM RaidAchievementsEntity WHERE profileId = :characterID")
    suspend fun getAllRaidAchievementsEntities(characterID: Long): List<RaidAchievementsEntity>

    @Query("SELECT * FROM RaidAchievementsEntity WHERE raid = :raid AND profileId = :characterID")
    suspend fun getRaidAchievementsEntity(raid: Raid, characterID: Long): RaidAchievementsEntity?
}
