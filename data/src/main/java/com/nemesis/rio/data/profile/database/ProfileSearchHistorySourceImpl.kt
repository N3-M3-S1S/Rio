package com.nemesis.rio.data.profile.database

import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.search.ProfileSearchHistoryItem
import com.nemesis.rio.domain.profile.search.ProfileSearchHistorySource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class ProfileSearchHistorySourceImpl<P : Profile>(
    private val profileDao: ProfileDao<P>,
    private val profileIDProvider: ProfileIDProvider<P>
) : ProfileSearchHistorySource<P> {

    override fun getSearchHistory(): Flow<List<ProfileSearchHistoryItem<P>>> {
        return profileDao.getProfilesWithSearchHistory()
    }

    override suspend fun addOrUpdate(profile: P) {
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
