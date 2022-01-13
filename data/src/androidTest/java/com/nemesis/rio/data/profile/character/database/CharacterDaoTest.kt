package com.nemesis.rio.data.profile.character.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Race
import com.nemesis.rio.domain.profile.character.attributes.Spec
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import sharedTest.createTestCharacter
import kotlin.test.*

class CharacterDaoTest : AppDatabaseTest() {
    private val characterDao = appDatabase.characterDao

    @Test
    fun searchCharacter() = runBlocking {
        val expectedCharacter = createTestCharacter()
        characterDao.saveOrUpdate(expectedCharacter)
        val characterFromDatabase =
            with(expectedCharacter) { characterDao.searchCharacter(name, region, realm) }
        assertEquals(expectedCharacter, characterFromDatabase)
    }

    @Test
    fun getCharacterID() = runBlocking {
        val testCharacter = createTestCharacter()
        val expectedID = characterDao.saveOrUpdate(testCharacter)
        val idFromDatabase = characterDao.getProfileID(testCharacter)
        assertEquals(expectedID, idFromDatabase)
    }

    @Test
    fun updateCharacter() = runBlocking {
        val testCharacter = createTestCharacter()
        characterDao.saveOrUpdate(testCharacter)
        val updatedAttributes = testCharacter.attributes.copy(
            activeSpec = Spec.BREWMASTER,
            race = Race.BLOOD_ELF
        )
        val updatedCharacter =
            Character(
                testCharacter.name,
                testCharacter.region,
                testCharacter.faction,
                testCharacter.url,
                testCharacter.realm,
                updatedAttributes,
                testCharacter.gear,
                testCharacter.imageUrl,
                testCharacter.guildName
            )

        characterDao.saveOrUpdate(updatedCharacter)
        val characterFromDatabase =
            with(testCharacter) { characterDao.searchCharacter(name, region, realm) }
        assertEquals(updatedCharacter, characterFromDatabase)
    }

    @Test
    fun getCharactersWithSearchHistory() = runBlocking {
        val expectedCharacterSearchHistory =
            listOf(createTestCharacter("1"), createTestCharacter("2"), createTestCharacter("3"))

        expectedCharacterSearchHistory.forEach {
            val characterID = characterDao.saveOrUpdate(it)
            characterDao.updateLastSearchDateTime(Clock.System.now(), characterID)
        }

        val characterSearchHistory =
            characterDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertEquals(expectedCharacterSearchHistory, characterSearchHistory)
    }

    @Test
    fun updateLastRefreshDateTime() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val expectedLastRefreshDateTime = Clock.System.now()

        characterDao.updateLastRefreshDateTime(expectedLastRefreshDateTime, characterID)

        val lastRefreshDateTimeFromDatabase = characterDao.getLastUpdateDateTime(characterID)
        assertEquals(expectedLastRefreshDateTime, lastRefreshDateTimeFromDatabase)
    }

    @Test
    fun setLastSearchDateTimeNull() = runBlocking {
        val characterID = createTestCharacterInDatabase()

        characterDao.updateLastSearchDateTime(Clock.System.now(), characterID)
        var characterSearchHistory =
            characterDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertEquals(characterSearchHistory.size, 1)

        characterDao.updateLastSearchDateTime(null, characterID)
        characterSearchHistory =
            characterDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertTrue(characterSearchHistory.isEmpty())
    }

    @Test
    fun deleteByID() = runBlocking {
        val testCharacter = createTestCharacter()

        characterDao.saveOrUpdate(testCharacter)
        var characterIDFromDatabase = characterDao.getProfileID(testCharacter)
        assertNotNull(characterIDFromDatabase)

        characterDao.deleteByID(characterIDFromDatabase)
        characterIDFromDatabase = characterDao.getProfileID(testCharacter)
        assertNull(characterIDFromDatabase)
    }

    @Test
    fun deleteAll() = runBlocking {
        val testCharacter1 = createTestCharacter()
        val testCharacter2 = createTestCharacter()
        characterDao.saveOrUpdate(testCharacter1)
        characterDao.saveOrUpdate(testCharacter2)

        var character1IDFromDatabase = characterDao.getProfileID(testCharacter1)
        var character2IDFromDatabase = characterDao.getProfileID(testCharacter2)

        assertNotNull(character1IDFromDatabase)
        assertNotNull(character2IDFromDatabase)

        characterDao.deleteAll()
        character1IDFromDatabase = characterDao.getProfileID(testCharacter1)
        character2IDFromDatabase = characterDao.getProfileID(testCharacter2)
        assertNull(character1IDFromDatabase)
        assertNull(character2IDFromDatabase)
    }


    @Test
    fun setLastSearchDateTimeNullForAll() = runBlocking {
        val expectedCharactersWithSearchHistoryCount = 3
        for (i in 0 until expectedCharactersWithSearchHistoryCount) {
            val characterID = createTestCharacterInDatabase(i.toString())
            characterDao.updateLastSearchDateTime(Clock.System.now(), characterID)
        }

        var characterSearchHistory =
            characterDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertEquals(expectedCharactersWithSearchHistoryCount, characterSearchHistory.size)

        characterDao.setLastSearchDateTimeNullForAll()
        characterSearchHistory =
            characterDao.getProfilesWithSearchHistory().first().map { it.profile }
        assertTrue(characterSearchHistory.isEmpty())
    }

}
