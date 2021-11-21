package com.nemesis.rio.data.mplus.scores.colors.update

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nemesis.rio.data.api.RioApiClient
import com.nemesis.rio.data.mplus.scores.colors.database.MythicPlusScoreColorDao
import com.nemesis.rio.data.mplus.scores.colors.database.MythicPlusScoreColorEntity
import com.nemesis.rio.data.mplus.seasons.database.SeasonsDao
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import timber.log.Timber

class MythicPlusScoreColorsUpdateWorker(
    private val rioApiClient: RioApiClient,
    private val seasonsDao: SeasonsDao,
    private val scoreColorsDao: MythicPlusScoreColorDao,
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    internal companion object {
        val WORK_NAME = MythicPlusScoreColorsUpdateWorker::class.simpleName!!
    }

    // Update colors only for current season because colors for previous seasons do not change after a season is over
    override suspend fun doWork(): Result {
        val currentSeason = seasonsDao.getLastAddedSeason()
        val currentSeasonApiValue = seasonsDao.getSeasonApiValue(currentSeason)
        Timber.d("Updating mythic plus score colors for season '$currentSeason' with api value '$currentSeasonApiValue'")
        return try {
            val scoresWithColors = rioApiClient.getMythicPlusScoresWithColors(currentSeasonApiValue)
            if (scoresWithColors.isNotEmpty()) {
                val currentSeasonId = seasonsDao.getSeasonIdByApiValue(currentSeasonApiValue)!!
                val scoreColorsEntities = mapScoresWithColorsToEntities(currentSeasonId, scoresWithColors)
                scoreColorsDao.deleteScoreColorsForSeason(currentSeason)
                scoreColorsDao.saveAll(scoreColorsEntities)
                Timber.d("Mythic plus score colors updated")
            } else {
                Timber.d("No mythic plus score colors found for the season")
            }
            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.retry()
        }
    }

    private fun mapScoresWithColorsToEntities(
        seasonId: Int,
        scoresWithColors: Map<MythicPlusScore, HexColor>
    ): List<MythicPlusScoreColorEntity> =
        scoresWithColors.map { (score, color) ->
            MythicPlusScoreColorEntity(seasonId, score, color)
        }
}
