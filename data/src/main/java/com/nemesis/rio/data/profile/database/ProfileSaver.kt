package com.nemesis.rio.data.profile.database

import com.nemesis.rio.domain.profile.Profile

class ProfileSaver<P : Profile>(
    private val profileDao: ProfileDao<P>,
    private val profileIdCache: ProfileIDCache
) {
    suspend fun saveOrUpdateProfileAndCacheID(profile: P) =
        profileDao.saveOrUpdate(profile).also { profileIdCache.add(profile, it) }
}
