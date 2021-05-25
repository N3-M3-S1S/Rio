package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.mplus.seasons.database.createTestSeasonEntitiesInDatabase
import com.nemesis.rio.data.mplus.seasons.database.testSeasonEntities
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.domain.profile.character.attributes.Role
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MythicPlusScoresDaoTest : AppDatabaseTest() {
    private val scoresDao = appDatabase.mythicPlusScoresDao
    private var characterID by Delegates.notNull<Long>()

    @BeforeTest
    fun setup() = runBlocking {
        appDatabase.clearAllTables()
        createTestSeasonEntitiesInDatabase(appDatabase.seasonsDao)
        characterID = createTestCharacterInDatabase()
    }

    @Test
    fun saveAndGetScores() = runBlocking {
        val randomSeasonEntity = testSeasonEntities.random()

        val expectedScores = MythicPlusScores(1f, mapOf(Role.HEALER to 2f, Role.TANK to 3f))
        val scoresPojos = mapOf(randomSeasonEntity.id to expectedScores)
            .toScoresPojos(characterID)
        scoresDao.saveOrUpdate(scoresPojos)

        val result = scoresDao.getScoresPojoForSeason(randomSeasonEntity.name, characterID)!!
            .toMythicPlusScores()
        assertEquals(expectedScores, result)
    }

    @Test
    fun updateAndGetScores() = runBlocking {
        val randomSeasonEntity = testSeasonEntities.random()
        val roleScores = mutableMapOf(Role.TANK to 100f)

        mapOf(randomSeasonEntity.id to MythicPlusScores(2f, roleScores))
            .toScoresPojos(characterID)
            .also { scoresDao.saveOrUpdate(it) }


        roleScores[Role.DAMAGE_DEALER] = 200f
        roleScores[Role.HEALER] = 300f

        val expectedScores = MythicPlusScores(1000f, roleScores)

        mapOf(randomSeasonEntity.id to expectedScores)
            .toScoresPojos(characterID)
            .also { scoresDao.saveOrUpdate(it) }

        val result = scoresDao.getScoresPojoForSeason(randomSeasonEntity.name, characterID)
            ?.toMythicPlusScores()
        assertEquals(expectedScores, result)

    }

    @Test
    fun getSeasonsWithScores() = runBlocking {
        val legionSeasonEntities = testSeasonEntities.filter { it.expansion == Expansion.LEGION }
        legionSeasonEntities.map { it.id }
            .associateWith { MythicPlusScores(1111f, mapOf(Role.HEALER to 9999f)) }
            .toScoresPojos(characterID)
            .also { scoresDao.saveOrUpdate(it) }

        val expected = legionSeasonEntities.map { it.name }

        val result = scoresDao.getSeasonsWithScores(characterID, Expansion.LEGION)
        assertEquals(expected.toSet(), result.toSet())
    }

}
