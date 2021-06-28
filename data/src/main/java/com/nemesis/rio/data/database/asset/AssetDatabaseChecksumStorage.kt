package com.nemesis.rio.data.database.asset

import android.content.SharedPreferences
import androidx.core.content.edit

class AssetDatabaseChecksumStorage(private val sharedPreferences: SharedPreferences) {
    private val assetDatabaseChecksumKey = "asset_db_checksum"

    fun getLastSavedChecksum(): String? =
        sharedPreferences.getString(assetDatabaseChecksumKey, null)

    fun saveChecksum(checksum: String) {
        sharedPreferences.edit { putString(assetDatabaseChecksumKey, checksum) }
    }

}
