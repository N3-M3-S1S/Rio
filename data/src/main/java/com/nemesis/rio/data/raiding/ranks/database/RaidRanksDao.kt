package com.nemesis.rio.data.raiding.ranks.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nemesis.rio.domain.raiding.Raid

@Dao
interface RaidRanksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(list: List<RaidRanksEntity>)

    @Query("SELECT * FROM RaidRanksEntity WHERE profileId = :guildID")
    suspend fun getAllRaidRanksEntities(guildID: Long): List<RaidRanksEntity>

    @Query("SELECT * FROM RaidRanksEntity WHERE raid = :raid AND profileId = :guildID")
    suspend fun getRaidRanksEntitiesForRaid(raid: Raid, guildID: Long): List<RaidRanksEntity>

    @Query("DELETE FROM RaidRanksEntity WHERE raid NOT IN (:raidsToKeep) AND profileId = :guildId")
    suspend fun deleteRanksIfRaidIsNotInList(raidsToKeep: List<Raid>, guildId: Long)
}
