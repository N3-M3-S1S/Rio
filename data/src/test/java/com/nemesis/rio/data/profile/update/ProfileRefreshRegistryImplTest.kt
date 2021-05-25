package com.nemesis.rio.data.profile.update

import com.nemesis.rio.data.api.refresh.ProfileLastCrawlDateTimeProvider
import com.nemesis.rio.data.profile.database.ProfileDao
import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.domain.profile.Profile
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Test
import sharedTest.createTestCharacter
import sharedTest.createTestGuild
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ProfileRefreshRegistryImplTest {

    @MockK
    lateinit var profileDao: ProfileDao<Profile>

    @MockK
    lateinit var profileIDProvider: ProfileIDProvider<Profile>

    @MockK
    lateinit var profileLastCrawlDateTimeProvider: ProfileLastCrawlDateTimeProvider<Profile>

    @InjectMockKs
    lateinit var profileRefreshRegistryImpl: ProfileUpdateRegistryImpl<Profile>

    init {
        MockKAnnotations.init(this)
        coEvery { profileIDProvider.getProfileID(any()) } returns 1
    }

    @Test
    fun testProfileUpdated() = runBlocking {
        val profile = createTestGuild()
        val lastRefreshInstant = Clock.System.now()
        val lastCrawlInstant = lastRefreshInstant - Duration.minutes(1)

        coEvery { profileDao.getLastUpdateDateTime(any()) } returns
                lastRefreshInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { profileLastCrawlDateTimeProvider.getProfileLastCrawlDateTime(any()) } returns
                lastCrawlInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        assertTrue(profileRefreshRegistryImpl.isProfileUpdated(profile))
    }

    @Test
    fun testProfileOutdated() = runBlocking {
        val profile = createTestCharacter()
        val lastRefreshInstant = Clock.System.now()
        val lastCrawlInstant = lastRefreshInstant + Duration.minutes(1)

        coEvery { profileDao.getLastUpdateDateTime(any()) } returns
                lastRefreshInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        coEvery { profileLastCrawlDateTimeProvider.getProfileLastCrawlDateTime(any()) } returns
                lastCrawlInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        assertFalse(profileRefreshRegistryImpl.isProfileUpdated(profile))
    }
}
