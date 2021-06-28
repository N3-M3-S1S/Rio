package com.nemesis.rio.data.database.asset

import android.content.res.AssetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class AssetDatabaseFileManager(private val assetManager: AssetManager, directoryPath: String) {
    private val assetDatabaseFilePath =
        "$directoryPath${File.separator}asset_db.sqlite"

    suspend fun createAssetDatabaseFile(): File = withContext(Dispatchers.IO) {
        getAssetDatabaseFile().apply { writeBytes(readAssetDatabaseBytes()) }
    }

    private fun getAssetDatabaseFile(): File = File(assetDatabaseFilePath)

    private fun readAssetDatabaseBytes() = assetManager.open("db.sqlite")
        .use(InputStream::readBytes)

}
