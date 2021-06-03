package com.nemesis.rio.data.profile.guild.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.nemesis.rio.data.profile.database.ProfileDao
import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryItem
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.domain.server.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class GuildDao : ProfileDao<Guild>() {
    override val tableName = GuildEntity.TABLE_NAME

    @Transaction
    override suspend fun saveOrUpdate(profile: Guild) =
        getProfileID(profile) ?: save(profile.toEntity())

    override suspend fun getProfileID(profile: Guild) =
        with(profile) { getGuildID(name, region, realm) }

    @Query("SELECT profileId FROM GuildEntity WHERE lower(name) = lower(:name) AND region = :region AND realm = :realm")
    abstract suspend fun getGuildID(name: String, region: Region, realm: Realm): Long?

    suspend fun searchGuild(name: String, region: Region, realm: Realm) =
        searchGuildEntity(name, region, realm)?.toGuild()

    override fun getProfilesWithSearchHistory() = getGuildEntitiesWithSearchHistory().map {
        it.map { guildEntity ->
            ProfileSearchHistoryItem(
                guildEntity.toGuild(),
                guildEntity.lastSearchDateTime!!
            )
        }
    }

    @Insert
    protected abstract fun save(guildEntity: GuildEntity): Long

    @Query("SELECT * FROM GuildEntity WHERE lastSearchDateTime NOT NULL")
    protected abstract fun getGuildEntitiesWithSearchHistory(): Flow<List<GuildEntity>>

    @Query("SELECT * FROM GuildEntity WHERE lower(name) = lower(:name) AND region = :region AND realm = :realm")
    protected abstract suspend fun searchGuildEntity(
        name: String,
        region: Region,
        realm: Realm
    ): GuildEntity?
}
