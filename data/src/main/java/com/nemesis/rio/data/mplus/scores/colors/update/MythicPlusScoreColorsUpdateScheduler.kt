package com.nemesis.rio.data.mplus.scores.colors.update

import androidx.work.*
import java.util.concurrent.TimeUnit

class MythicPlusScoreColorsUpdateScheduler(private val workManager: WorkManager) {

    fun scheduleMythicPlusScoreColorsUpdate() {
        val workRequest = buildMythicPlusScoreColorsUpdateWorkRequest()
        workManager.enqueueUniquePeriodicWork(
            MythicPlusScoreColorsUpdateWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun buildMythicPlusScoreColorsUpdateWorkRequest(): PeriodicWorkRequest =
        PeriodicWorkRequestBuilder<MythicPlusScoreColorsUpdateWorker>(3, TimeUnit.DAYS)
            .setConstraints(buildMythicPlusScoreColorsUpdateWorkRequestConstraints())
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .build()

    private fun buildMythicPlusScoreColorsUpdateWorkRequestConstraints(): Constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
}
