package com.nemesis.rio.data.profile.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

abstract class ProfileEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN_NAME)
    var id: Long = 0

    @ColumnInfo(name = LAST_REFRESH_DATETIME_COLUMN_NAME)
    var lastRefreshDateTime: Instant = Clock.System.now()

    @ColumnInfo(name = LAST_SEARCH_DATETIME_COLUMN_NAME)
    var lastSearchDateTime: Instant? = null

    companion object {
        const val ID_COLUMN_NAME = "profileID"
        const val LAST_REFRESH_DATETIME_COLUMN_NAME = "lastRefreshDateTime"
        const val LAST_SEARCH_DATETIME_COLUMN_NAME = "lastSearchDateTime"
    }
}
