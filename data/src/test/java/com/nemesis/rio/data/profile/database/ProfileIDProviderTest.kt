package com.nemesis.rio.data.profile.database

import com.nemesis.rio.domain.profile.Profile
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import sharedTest.createTestCharacter
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class ProfileIDProviderTest {
    @RelaxedMockK
    lateinit var dao: ProfileDao<Profile>

    @RelaxedMockK
    lateinit var cache: ProfileIDCache

    @InjectMockKs
    lateinit var profileIDProvider: ProfileIDProvider<Profile>

    @BeforeTest
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getIdFromDao() = runBlocking {
        val expectedId = 1L

        every { cache.get(any()) } returns null
        coEvery { dao.getProfileID(any()) } returns expectedId

        val result = profileIDProvider.getProfileID(createTestCharacter())

        assertEquals(expectedId, result)
    }

    @Test
    fun getIdFromCache() = runBlocking {
        val expectedId = 1L

        coEvery { dao.getProfileID(any()) } returns null
        every { cache.get(any()) } returns expectedId

        val result = profileIDProvider.getProfileID(createTestCharacter())

        assertEquals(expectedId, result)
    }
}
