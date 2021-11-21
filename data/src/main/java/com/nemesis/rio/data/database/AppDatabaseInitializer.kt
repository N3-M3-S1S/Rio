package com.nemesis.rio.data.database

import android.database.sqlite.SQLiteDatabase
import com.nemesis.rio.data.database.asset.AppDatabaseTableAssetUpdater
import com.nemesis.rio.data.database.asset.AssetDatabaseChangesInspector
import com.nemesis.rio.data.database.asset.AssetDatabaseFileManager
import java.io.File

class AppDatabaseInitializer(
    private val assetDatabaseChangesInspector: AssetDatabaseChangesInspector,
    private val assetDatabaseFileManager: AssetDatabaseFileManager,
    private val tableUpdaters: List<AppDatabaseTableAssetUpdater>
) {

    suspend fun initializeAppDatabase() {
        val assetDatabaseFile = assetDatabaseFileManager.createAssetDatabaseFile()
        if (assetDatabaseChangesInspector.assetDatabaseFileChangedSinceLastHandle(assetDatabaseFile)) {
            updateAppDatabaseTablesWithAssetDatabase(assetDatabaseFile)
        }
        assetDatabaseChangesInspector.setAssetDatabaseChangesHandled(assetDatabaseFile)
        assetDatabaseFile.delete()
    }

    private suspend fun updateAppDatabaseTablesWithAssetDatabase(assetDatabaseFile: File) {
        val assetDatabase = openAssetDatabase(assetDatabaseFile)
        tableUpdaters.forEach {
            it.updateAppDatabaseWithAssetDatabase(assetDatabase)
        }
        assetDatabase.close()
    }

    private fun openAssetDatabase(assetDatabaseFile: File): SQLiteDatabase =
        SQLiteDatabase.openDatabase(assetDatabaseFile.path, null, SQLiteDatabase.OPEN_READONLY)
}
