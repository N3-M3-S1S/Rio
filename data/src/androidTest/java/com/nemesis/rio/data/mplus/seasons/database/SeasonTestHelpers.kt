package com.nemesis.rio.data.mplus.seasons.database

import com.nemesis.rio.domain.game.Expansion
import kotlinx.coroutines.runBlocking

val testSeasonEntities = enumValues<Expansion>().flatMapIndexed { index, expansion ->
    (1..3).map { i ->
        SeasonEntity("season-$i-$expansion", "Season $expansion $i", expansion).also {
            it.id = ((index * 10) + i).toLong()
        }
    }
}


fun createTestSeasonEntitiesInDatabase(seasonsDao: SeasonsDao) {
    runBlocking { seasonsDao.save(testSeasonEntities) }
}
