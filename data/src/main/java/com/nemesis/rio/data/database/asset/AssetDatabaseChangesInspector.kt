package com.nemesis.rio.data.database.asset

import com.nemesis.rio.utils.checksum.FileChecksumGenerator
import timber.log.Timber
import java.io.File

class AssetDatabaseChangesInspector(
    private val assetDatabaseChecksumStorage: AssetDatabaseChecksumStorage,
    private val fileChecksumGenerator: FileChecksumGenerator
) {

    suspend fun assetDatabaseFileChangedSinceLastHandle(assetDatabaseFile: File): Boolean {
        val assetDatabaseChecksumSinceLastCheck =
            assetDatabaseChecksumStorage.getLastSavedChecksum()
        val assetDatabaseChecksum = fileChecksumGenerator.generateChecksum(assetDatabaseFile)
        Timber.d("Asset database checksum: '$assetDatabaseChecksum', Checksum since last check: '$assetDatabaseChecksumSinceLastCheck'")
        return assetDatabaseChecksum != assetDatabaseChecksumSinceLastCheck
    }

    suspend fun setAssetDatabaseChangesHandled(assetDatabaseFile: File) {
        val assetDatabaseChecksum = fileChecksumGenerator.generateChecksum(assetDatabaseFile)
        assetDatabaseChecksumStorage.saveChecksum(assetDatabaseChecksum)
    }
}
