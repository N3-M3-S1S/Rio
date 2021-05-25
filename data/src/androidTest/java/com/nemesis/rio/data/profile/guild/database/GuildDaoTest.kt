package com.nemesis.rio.data.profile.guild.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestGuildInDatabase
import com.nemesis.rio.utils.now
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import sharedTest.createTestGuild
import kotlin.test.*

class GuildDaoTest : AppDatabaseTest() {
    private val guildDao = appDatabase.guildDao

    @Test
    fun searchGuild() = runBlocking {
        val expectedGuild = createTestGuild()

        guildDao.saveOrUpdate(expectedGuild)

        val guildFromDatabase = with(expectedGuild) { guildDao.searchGuild(name, region, realm) }
        assertEquals(expectedGuild, guildFromDatabase)
    }

    @Test
    fun getGuildID() = runBlocking {
        val testGuild = createTestGuild()

        val expectedID = guildDao.saveOrUpdate(testGuild)

        val idFromDatabase = guildDao.getProfileID(testGuild)
        assertEquals(expectedID, idFromDatabase)
    }

    @Test
    fun getGuildsWithSearchHistory() = runBlocking {
        val guilds =
            listOf(createTestGuild("1"), createTestGuild("2"), createTestGuild("3"))

        guilds.forEach {
            val guildID = guildDao.saveOrUpdate(it)
            guildDao.updateLastSearchDateTime(LocalDateTime.now(), guildID)
        }

        val guildSearchHistory =
            guildDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertEquals(guilds, guildSearchHistory)
    }

    @Test
    fun updateLastRefreshDateTime() = runBlocking {
        val guildID = createTestGuildInDatabase()
        val expectedLastRefreshDateTime = LocalDateTime.now()

        guildDao.updateLastRefreshDateTime(expectedLastRefreshDateTime, guildID)

        val lastRefreshDateTimeFromDatabase = guildDao.getLastUpdateDateTime(guildID)
        assertEquals(expectedLastRefreshDateTime, lastRefreshDateTimeFromDatabase)
    }

    @Test
    fun setLastSearchDateTimeNull() = runBlocking {
        val guildID = createTestGuildInDatabase()

        guildDao.updateLastSearchDateTime(LocalDateTime.now(), guildID)
        var guildSearchHistory = guildDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertEquals(guildSearchHistory.size, 1)

        guildDao.updateLastSearchDateTime(null, guildID)
        guildSearchHistory = guildDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertTrue(guildSearchHistory.isEmpty())
    }

    @Test
    fun deleteByID() = runBlocking {
        val testGuild = createTestGuild()

        guildDao.saveOrUpdate(testGuild)
        var guildIDFromDatabase = guildDao.getProfileID(testGuild)
        assertNotNull(guildIDFromDatabase)

        guildDao.deleteByID(guildIDFromDatabase)
        guildIDFromDatabase = guildDao.getProfileID(testGuild)
        assertNull(guildIDFromDatabase)
    }

    @Test
    fun deleteAll() = runBlocking {
        val testGuild1 = createTestGuild()
        val testGuild2 = createTestGuild()
        guildDao.saveOrUpdate(testGuild1)
        guildDao.saveOrUpdate(testGuild2)

        var guild1IDFromDatabase = guildDao.getProfileID(testGuild1)
        var guild2IDFromDatabase = guildDao.getProfileID(testGuild2)
        assertNotNull(guild1IDFromDatabase)
        assertNotNull(guild2IDFromDatabase)

        guildDao.deleteAll()
        guild1IDFromDatabase = guildDao.getProfileID(testGuild1)
        guild2IDFromDatabase = guildDao.getProfileID(testGuild2)
        assertNull(guild1IDFromDatabase)
        assertNull(guild2IDFromDatabase)
    }


    @Test
    fun setLastSearchDateTimeNullForAll() = runBlocking {
        val expectedGuildsWithSearchHistoryCount = 3
        for (i in 0 until expectedGuildsWithSearchHistoryCount) {
            val guildID = createTestGuildInDatabase(i.toString())
            guildDao.updateLastSearchDateTime(LocalDateTime.now(), guildID)
        }

        var guildSearchHistory = guildDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertEquals(expectedGuildsWithSearchHistoryCount, guildSearchHistory.size)

        guildDao.setLastSearchDateTimeNullForAll()
        guildSearchHistory = guildDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertTrue(guildSearchHistory.isEmpty())
    }

}
