package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.mplus.seasons.database.SeasonsDao
import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores

class MythicPlusScoresSaver(
    private val scoresDao: MythicPlusScoresDao,
    private val seasonDao: SeasonsDao
) :
    ProfileProgressSaver<Map<String, MythicPlusScores>> {

    override suspend fun saveOrUpdate(progress: Map<String, MythicPlusScores>, profileId: Long) {
        (progress
            .mapKeys { seasonDao.getSeasonIDForApiValue(it.key) }
            .filterKeys { it != null } as Map<Long, MythicPlusScores>)
            .toScoresPojos(profileId)
            .let { scoresDao.saveOrUpdate(it) }
    }
}
