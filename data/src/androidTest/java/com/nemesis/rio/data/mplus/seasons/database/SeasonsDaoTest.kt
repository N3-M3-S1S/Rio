package com.nemesis.rio.data.mplus.seasons.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.domain.game.Expansion
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SeasonsDaoTest : AppDatabaseTest() {
    private val seasonsDao = appDatabase.seasonsDao

    @BeforeTest
    fun setup() {
        appDatabase.clearAllTables()
        createTestSeasonEntitiesInDatabase(appDatabase.seasonsDao)
    }

    @Test
    fun getSeasonsForExpansion() = runBlocking {
        enumValues<Expansion>().forEach { expansion ->
            val result = seasonsDao.getSeasonsForExpansion(expansion)
            val expectedSeasons =
                testSeasonEntities.filter { it.expansion == expansion }.map { it.name }
            assertEquals(expectedSeasons.size, result.size)
            assertEquals(expectedSeasons, result)
        }
    }

    @Test
    fun getIDForSeasonApiValue() = runBlocking {
        testSeasonEntities.forEach { seasonEntity ->
            val result = seasonsDao.getSeasonIDForApiValue(seasonEntity.apiValue)
            val expectedSeasonID = seasonEntity.id
            assertEquals(expectedSeasonID, result)
        }

    }

    @Test
    fun getActualSeason() = runBlocking {
        val result = seasonsDao.getLastSeason()
        val expectedSeason = testSeasonEntities.maxByOrNull { it.id }?.name
        assertEquals(expectedSeason, result)
    }

}
