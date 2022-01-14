package com.nemesis.rio.data.profile.database

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistory
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class ProfileSearchHistoryDatabaseRepository<P : Profile>(
    private val profileDao: ProfileDao<P>,
    private val profileIDProvider: ProfileIDProvider<P>
) : ProfileSearchHistoryRepository<P> {

    override fun getSearchHistory(): Flow<ProfileSearchHistory<P>> {
        return profileDao.getProfilesWithSearchHistory()
    }

    override suspend fun add(profile: P) {
        profileIDProvider.withProfileID(profile) {
            profileDao.updateLastSearchDateTime(Clock.System.now(), it)
        }
    }

    override suspend fun remove(profile: P) {
        profileIDProvider.withProfileID(profile) {
            profileDao.deleteByID(it)
        }
    }
}
