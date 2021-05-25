package com.nemesis.rio.data.progress.database

interface ProfileProgressSaver<P> {
    suspend fun saveOrUpdate(progress: P, profileId: Long)
}
