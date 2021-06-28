package com.nemesis.rio.data.database.asset

import android.database.sqlite.SQLiteDatabase

interface AppDatabaseTableAssetUpdater {
    suspend fun updateAppDatabaseWithAssetDatabase(assetDatabase: SQLiteDatabase)
}
