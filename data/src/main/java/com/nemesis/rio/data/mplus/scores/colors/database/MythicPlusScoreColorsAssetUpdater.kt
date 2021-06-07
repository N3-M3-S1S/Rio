package com.nemesis.rio.data.mplus.scores.colors.database

import android.database.sqlite.SQLiteDatabase
import com.nemesis.rio.data.database.asset.AppDatabaseTableAssetUpdater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MythicPlusScoreColorsAssetUpdater(private val mythicPlusScoreColorDao: MythicPlusScoreColorDao) :
    AppDatabaseTableAssetUpdater {

    override suspend fun updateAppDatabaseWithAssetDatabase(assetDatabase: SQLiteDatabase) {
        val scoreColorEntities = getScoreColorEntitiesFromAssetDatabase(assetDatabase)
        mythicPlusScoreColorDao.deleteAll()
        mythicPlusScoreColorDao.saveAll(scoreColorEntities)
    }

    private suspend fun getScoreColorEntitiesFromAssetDatabase(assetDatabase: SQLiteDatabase): List<MythicPlusScoreColorEntity> =
        withContext(Dispatchers.IO) {
            val scoreColorEntities = mutableListOf<MythicPlusScoreColorEntity>()

            assetDatabase.rawQuery("SELECT * FROM ${MythicPlusScoreColorEntity.TABLE_NAME}", null)
                .use { cursor ->
                    while (cursor.moveToNext()) {
                        val seasonId =
                            cursor.getInt(cursor.getColumnIndex(MythicPlusScoreColorEntity.SEASON_ID_COLUMN))
                        val score =
                            cursor.getFloat(cursor.getColumnIndex(MythicPlusScoreColorEntity.SCORE_COLUMN))
                        val hexColor =
                            cursor.getString(cursor.getColumnIndex(MythicPlusScoreColorEntity.HEX_COLOR_COLUMN))
                        val scoreColorEntity = MythicPlusScoreColorEntity(seasonId, score, hexColor)
                        scoreColorEntities.add(scoreColorEntity)
                    }
                }

            scoreColorEntities
        }

}
