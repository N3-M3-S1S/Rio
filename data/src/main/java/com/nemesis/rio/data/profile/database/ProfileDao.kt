package com.nemesis.rio.data.profile.database

import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.room.RawQuery
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.nemesis.rio.data.database.DateTimeConverters
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

abstract class ProfileDao<P : Profile> {
    protected abstract val tableName: String
    private lateinit var roomDatabase: RoomDatabase
    private lateinit var writableDatabase: SupportSQLiteDatabase

    fun setRoomDatabase(database: RoomDatabase) {
        roomDatabase = database
        writableDatabase =
            database.getOpenHelper().writableDatabase
    }

    suspend fun updateLastRefreshDateTime(newRefreshDateTime: Instant, profileID: Long) {
        updateDateTimeColumn(
            ProfileEntity.LAST_REFRESH_DATETIME_COLUMN_NAME,
            newRefreshDateTime,
            profileID
        )
    }

    suspend fun updateLastSearchDateTime(newSearchDateTime: Instant?, profileID: Long) {
        updateDateTimeColumn(
            ProfileEntity.LAST_SEARCH_DATETIME_COLUMN_NAME,
            newSearchDateTime,
            profileID
        )
    }

    open suspend fun deleteByID(profileID: Long) {
        roomDatabase.withTransaction {
            writableDatabase.delete(
                tableName,
                "${ProfileEntity.ID_COLUMN_NAME} = ?",
                arrayOf(profileID)
            )
        }
    }

    suspend fun deleteAll() {
        roomDatabase.withTransaction {
            writableDatabase.delete(
                tableName,
                null,
                null
            )
        }
    }

    suspend fun setLastSearchDateTimeNullForAll() {
        roomDatabase.withTransaction {
            writableDatabase.update(
                tableName,
                SQLiteDatabase.CONFLICT_FAIL,
                contentValuesOf(ProfileEntity.LAST_SEARCH_DATETIME_COLUMN_NAME to null),
                null,
                null
            )
        }
    }

    suspend fun getLastUpdateDateTime(profileID: Long): Instant {
        val query = SupportSQLiteQueryBuilder.builder(tableName)
            .columns(arrayOf(ProfileEntity.LAST_REFRESH_DATETIME_COLUMN_NAME))
            .selection("${ProfileEntity.ID_COLUMN_NAME} = ?", arrayOf(profileID))
            .create()
        return getLastRefreshDateTimeRaw(query)
    }

    @RawQuery
    protected abstract suspend fun getLastRefreshDateTimeRaw(query: SupportSQLiteQuery): Instant

    abstract suspend fun saveOrUpdate(profile: P): Long

    abstract suspend fun getProfileID(profile: P): Long?

    abstract fun getProfilesWithSearchHistory(): Flow<ProfileSearchHistory<P>>

    private suspend fun updateDateTimeColumn(
        columnName: String,
        newDateTime: Instant?,
        profileID: Long
    ) {
        roomDatabase.withTransaction {
            val instantColumnValue = DateTimeConverters.instantToLong(newDateTime)
            writableDatabase.update(
                tableName,
                SQLiteDatabase.CONFLICT_FAIL,
                contentValuesOf(columnName to instantColumnValue),
                "${ProfileEntity.ID_COLUMN_NAME} = ?",
                arrayOf(profileID)
            )
        }
    }
}
