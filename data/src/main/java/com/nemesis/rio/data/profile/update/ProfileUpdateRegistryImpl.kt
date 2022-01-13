package com.nemesis.rio.data.profile.update

import com.nemesis.rio.data.api.refresh.ProfileLastCrawlDateTimeProvider
import com.nemesis.rio.data.profile.database.ProfileDao
import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.domain.profile.update.ProfileUpdateRegistry
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class ProfileUpdateRegistryImpl<P : Profile>(
    private val profileDao: ProfileDao<P>,
    private val profileIDProvider: ProfileIDProvider<P>,
    private val profileLastCrawlDateTimeProvider: ProfileLastCrawlDateTimeProvider<P>
) : ProfileUpdateRegistry<P> {

    override suspend fun registerProfileUpdated(profile: P) {
        profileIDProvider.withProfileID(profile) {
            profileDao.updateLastRefreshDateTime(Clock.System.now(), it)
        }
    }

    override suspend fun getLastUpdateDateTime(profile: P): Instant =
        profileIDProvider.withProfileID(profile) {
            profileDao.getLastUpdateDateTime(it)
        }

    override suspend fun isProfileUpdated(profile: P): Boolean {
        val lastUpdateDateTime = getLastUpdateDateTime(profile)
        val lastCrawlDateTime =
            profileLastCrawlDateTimeProvider.getProfileLastCrawlInstant(profile)
        return lastUpdateDateTime > lastCrawlDateTime
    }
}
