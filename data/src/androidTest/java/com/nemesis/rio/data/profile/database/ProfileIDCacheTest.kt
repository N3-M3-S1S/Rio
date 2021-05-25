package com.nemesis.rio.data.profile.database

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import sharedTest.createTestCharacter
import sharedTest.createTestGuild
import kotlin.test.BeforeTest

class ProfileIDCacheTest {
    private lateinit var profileIDCache: ProfileIDCache

    @BeforeTest
    fun init() {
        profileIDCache = ProfileIDCache()
    }

    @Test
    fun get() {
        val testCharacter = createTestCharacter()
        val expectedCharacterId = 1L
        profileIDCache.add(testCharacter, expectedCharacterId)

        val testGuild = createTestGuild()
        val expectedGuildId = 2L
        profileIDCache.add(testGuild, expectedGuildId)

        val resultCharacterId = profileIDCache.get(testCharacter)

        assertEquals(expectedCharacterId, resultCharacterId)

        val resultGuildId = profileIDCache.get(testGuild)

        assertEquals(expectedGuildId, resultGuildId)
    }

    @Test
    fun remove() {
        val testCharacter = createTestCharacter()
        profileIDCache.add(testCharacter, 1)

        profileIDCache.remove(testCharacter)

        assertNull(profileIDCache.get(testCharacter))
    }

    @Test
    fun clear() {
        val testCharacter = createTestCharacter()
        val testGuild = createTestGuild()
        profileIDCache.add(testCharacter, 1)
        profileIDCache.add(testGuild, 1)

        profileIDCache.clear()

        assertNull(profileIDCache.get(testCharacter))
        assertNull(profileIDCache.get(testGuild))
    }
}
