package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.mplus.scores.serialization.MythicPlusScoresContainer
import com.nemesis.rio.data.mplus.seasons.database.SeasonsDao
import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import timber.log.Timber

class MythicPlusScoresSaver(
    private val scoresDao: MythicPlusScoresDao,
    private val seasonDao: SeasonsDao
) :
    ProfileProgressSaver<List<MythicPlusScoresContainer>> {

    override suspend fun saveOrUpdate(progress: List<MythicPlusScoresContainer>, profileId: Long) {
        scoresDao.deleteAllScores(profileId)
        val overallScoreEntities = mutableListOf<MythicPlusOverallScoreEntity>()
        val roleScoreEntities = mutableListOf<MythicPlusRoleScoreEntity>()

        for ((seasonApiValue, overallScore, roleScores) in progress) {
            val seasonId = seasonDao.getSeasonIdByApiValue(seasonApiValue)
            if (seasonId == null) {
                Timber.d("No season id for api value '$seasonApiValue', scores with this api value will not be saved")
                continue
            }
            val overallScoreEntity = MythicPlusOverallScoreEntity(overallScore, seasonId, profileId)
            overallScoreEntities.add(overallScoreEntity)
            roleScores.mapTo(roleScoreEntities) { (role, score) ->
                MythicPlusRoleScoreEntity(role, score, seasonId, profileId)
            }
        }

        scoresDao.saveOverallScores(overallScoreEntities)
        scoresDao.saveRoleScoreEntities(roleScoreEntities)
    }
}
