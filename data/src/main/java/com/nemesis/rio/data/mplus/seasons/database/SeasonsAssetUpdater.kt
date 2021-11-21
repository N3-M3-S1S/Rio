package com.nemesis.rio.data.mplus.seasons.database

import android.database.sqlite.SQLiteDatabase
import com.nemesis.rio.data.database.asset.AppDatabaseTableAssetUpdater
import com.nemesis.rio.data.game.database.ExpansionConverters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeasonsAssetUpdater(private val seasonsDao: SeasonsDao) : AppDatabaseTableAssetUpdater {

    override suspend fun updateAppDatabaseWithAssetDatabase(assetDatabase: SQLiteDatabase) {
        val seasonEntities = getSeasonEntitiesFromAssetDatabase(assetDatabase)
        seasonsDao.save(seasonEntities)
    }

    private suspend fun getSeasonEntitiesFromAssetDatabase(assetDatabase: SQLiteDatabase): List<SeasonEntity> =
        withContext(Dispatchers.IO) {
            val seasonEntities = mutableListOf<SeasonEntity>()

            assetDatabase.rawQuery("SELECT * FROM ${SeasonEntity.TABLE_NAME}", null).use { cursor ->
                while (cursor.moveToNext()) {
                    val apiValue =
                        cursor.getString(cursor.getColumnIndex(SeasonEntity.API_VALUE_COLUMN))
                    val name = cursor.getString(cursor.getColumnIndex(SeasonEntity.NAME_COLUMN))
                    val expansionId =
                        cursor.getInt(cursor.getColumnIndex(SeasonEntity.EXPANSION_COLUMN))
                    val expansion = ExpansionConverters.idToExpansion(expansionId)
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val seasonEntity = SeasonEntity(apiValue, name, expansion, id)
                    seasonEntities.add(seasonEntity)
                }
            }

            seasonEntities
        }

}
