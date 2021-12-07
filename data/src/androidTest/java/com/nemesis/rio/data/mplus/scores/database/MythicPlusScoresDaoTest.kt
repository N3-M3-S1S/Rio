package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.data.mplus.seasons.database.createTestSeasonEntitiesInDatabase
import com.nemesis.rio.data.mplus.seasons.database.testSeasonEntities
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates
import kotlin.test.*

class MythicPlusScoresDaoTest : AppDatabaseTest() {
    private val scoresDao = appDatabase.mythicPlusScoresDao
    private var characterId by Delegates.notNull<Long>()

    @BeforeTest
    fun setup() = runBlocking {
        appDatabase.clearAllTables()
        createTestSeasonEntitiesInDatabase(appDatabase.seasonsDao)
        characterId = createTestCharacterInDatabase()
    }

    @Test
    fun saveAndGetOverallScore() = runBlocking {
        val randomSeasonEntity = testSeasonEntities.random()

        val expectedOverallScore = 123F
        val overallScoreEntity =
            MythicPlusOverallScoreEntity(expectedOverallScore, randomSeasonEntity.id, characterId)
        scoresDao.saveOverallScores(listOf(overallScoreEntity))

        val result = scoresDao.getOverallScore(characterId, randomSeasonEntity.name)
        assertEquals(expectedOverallScore, result)
    }

    @Test
    fun getOverallScoreForUnknownSeason() = runBlocking {
        val result = scoresDao.getOverallScore(characterId, "unknown-season")
        assertNull(result)
    }

    @Test
    fun saveAndGetRoleScores() = runBlocking {
        val randomSeasonEntity = testSeasonEntities.random()
        val expectedRoleScores =
            mapOf(Role.DAMAGE_DEALER to 100f, Role.HEALER to 1000f, Role.TANK to 10000f)

        val roleScoreEntities = expectedRoleScores.map { (role, score) ->
            MythicPlusRoleScoreEntity(
                role,
                score,
                randomSeasonEntity.id,
                characterId
            )
        }
        scoresDao.saveRoleScoreEntities(roleScoreEntities)

        val result =
            scoresDao.getRoleScoreEntities(characterId, randomSeasonEntity.name).toRoleScores()
        assertEquals(expectedRoleScores, result)
    }

    @Test
    fun getRoleScoresForUnknownSeason() = runBlocking {
        val result = scoresDao.getRoleScoreEntities(characterId, "unknown-season")
        assertTrue(result.isEmpty())
    }

    @Test
    fun saveAndGetSpecScores() = runBlocking {
        val randomSeasonEntity = testSeasonEntities.random()
        val expectedSpecScores = mapOf(Spec.BLOOD to 100F, Spec.UNHOLY to 12.3F)

        val specScoreEntities = expectedSpecScores.map { (spec, score) ->
            MythicPlusSpecScoreEntity(spec, score, randomSeasonEntity.id, characterId)
        }
        scoresDao.saveSpecScoreEntities(specScoreEntities)

        val result =
            scoresDao.getSpecScoreEntities(characterId, randomSeasonEntity.name).toSpecScores()
        assertEquals(expectedSpecScores, result)
    }

    @Test
    fun getSpecScoresForUnknownSeason() = runBlocking {
        val result = scoresDao.getSpecScoreEntities(characterId, "unknown-season")
        assertTrue(result.isEmpty())
    }

    @Test
    fun deleteAllScores() = runBlocking {
        val randomSeasonEntity = testSeasonEntities.random()
        val mythicPlusOverallScoreEntity =
            MythicPlusOverallScoreEntity(123F, randomSeasonEntity.id, characterId)
        scoresDao.saveOverallScores(listOf(mythicPlusOverallScoreEntity))
        scoresDao.deleteAllScores(characterId)

        val result = scoresDao.getOverallScore(characterId, randomSeasonEntity.name)
        assertNull(result)
    }


    @Test
    fun getSeasonsWithScores() = runBlocking {
        val legionSeasonEntities = testSeasonEntities.filter { it.expansion == Expansion.LEGION }
        legionSeasonEntities
            .map { MythicPlusOverallScoreEntity(123F, it.id, characterId) }
            .also { scoresDao.saveOverallScores(it) }


        val expected = legionSeasonEntities.groupBy(SeasonEntity::expansion)
            .mapValues { entry -> entry.value.map { seasonEntity -> seasonEntity.name } }

        val result = scoresDao.getSeasonsWithScores(characterId)

        assertEquals(expected, result)
    }

}
