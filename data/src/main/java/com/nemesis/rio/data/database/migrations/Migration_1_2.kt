package com.nemesis.rio.data.database.migrations

import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.nemesis.rio.data.mplus.scores.database.MythicPlusOverallScoreEntity
import com.nemesis.rio.data.mplus.scores.database.MythicPlusRoleScoreEntity
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoreBaseEntity
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore

@Suppress("ClassName")
object Migration_1_2 : Migration(1, 2) {
    private const val mythicPlusOverallScoreParentEntityTableName =
        "MythicPlusOverallScoreParentEntity"
    private const val mythicPlusRoleScoreChildEntityTableName =
        "MythicPlusRoleScoreChildEntity"

    override fun migrate(database: SupportSQLiteDatabase) {
        val mythicPlusScores = getMythicPlusScoresFromOldTables(database)
        deleteOldMythicPlusScoreEntitiesTables(database)
        createNewMythicPlusScoreEntitiesTables(database)
        saveMythicPlusScoresInNewTables(database, mythicPlusScores)
    }

    private fun getMythicPlusScoresFromOldTables(database: SupportSQLiteDatabase): List<CharacterMythicPlusScoresContainer> {
        val mythicPlusScoresFromOldTables = mutableListOf<CharacterMythicPlusScoresContainer>()

        val selectMythicPlusOverallScoreParentEntitiesQuery =
            buildSelectMythicPlusOverallScoreParentEntitiesQuery()

        database.query(selectMythicPlusOverallScoreParentEntitiesQuery).use { cursor ->
            while (cursor.moveToNext()) {
                val characterId = cursor.getLong(
                    cursor.getColumnIndex(ProfileEntity.ID_COLUMN_NAME)
                )
                val seasonId = cursor.getLong(
                    cursor.getColumnIndex("seasonId")
                )
                val overallScore = cursor.getFloat(
                    cursor.getColumnIndex("overallScore")
                )
                val overallScoreId = cursor.getLong(cursor.getColumnIndex("id"))
                val roleScores = getRoleScoresFromOldTable(database, overallScoreId)

                val characterMythicPlusScoresContainer = CharacterMythicPlusScoresContainer(
                    characterId,
                    seasonId,
                    overallScore,
                    roleScores
                )
                mythicPlusScoresFromOldTables.add(characterMythicPlusScoresContainer)
            }
        }

        return mythicPlusScoresFromOldTables
    }

    private fun buildSelectMythicPlusOverallScoreParentEntitiesQuery(): SupportSQLiteQuery =
        SupportSQLiteQueryBuilder
            .builder(mythicPlusOverallScoreParentEntityTableName)
            .create()

    private fun getRoleScoresFromOldTable(
        database: SupportSQLiteDatabase,
        overallScoreId: Long
    ): Map<String, MythicPlusScore> {
        val roleScores = mutableMapOf<String, MythicPlusScore>()

        val selectMythicPlusRoleScoreChildEntities =
            buildSelectRoleScoreChildEntitiesQuery(overallScoreId)

        database.query(selectMythicPlusRoleScoreChildEntities).use { cursor ->
            while (cursor.moveToNext()) {
                val roleValue = cursor.getString(cursor.getColumnIndex("role"))
                val score = cursor.getFloat(cursor.getColumnIndex("score"))
                roleScores[roleValue] = score
            }
        }

        return roleScores
    }

    private fun buildSelectRoleScoreChildEntitiesQuery(overallScoreId: Long): SupportSQLiteQuery =
        SupportSQLiteQueryBuilder
            .builder(mythicPlusRoleScoreChildEntityTableName)
            .columns(arrayOf("role", "score"))
            .selection("overallScoreId = ?", arrayOf(overallScoreId))
            .create()


    private fun deleteOldMythicPlusScoreEntitiesTables(database: SupportSQLiteDatabase) {
        deleteMythicPlusOverallScoreParentEntityTable(database)
        deleteMythicPlusRoleScoreChildEntityTable(database)
    }

    private fun deleteMythicPlusOverallScoreParentEntityTable(database: SupportSQLiteDatabase) {
        val query = "DROP TABLE $mythicPlusOverallScoreParentEntityTableName"
        database.execSQL(query)
    }

    private fun deleteMythicPlusRoleScoreChildEntityTable(database: SupportSQLiteDatabase) {
        val query = "DROP TABLE $mythicPlusRoleScoreChildEntityTableName"
        database.execSQL(query)
    }

    private fun createNewMythicPlusScoreEntitiesTables(database: SupportSQLiteDatabase) {
        createMythicPlusOverallScoreEntityTable(database)
        createMythicPlusRoleScoreEntityTable(database)
    }

    private fun createMythicPlusOverallScoreEntityTable(database: SupportSQLiteDatabase) {
        val createMythicPlusOverallScoreEntityTableQuery =
            buildCreateMythicPlusOverallScoreEntityTableQuery()
        database.execSQL(createMythicPlusOverallScoreEntityTableQuery)
    }

    private fun buildCreateMythicPlusOverallScoreEntityTableQuery(): String =
        "CREATE TABLE IF NOT EXISTS ${MythicPlusOverallScoreEntity.TABLE_NAME}(" +
                "${MythicPlusScoreBaseEntity.SCORE_COLUMN} REAL NOT NULL," +
                "${MythicPlusScoreBaseEntity.SEASON_ID_COLUMN} INTEGER NOT NULL," +
                "${MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN} INTEGER NOT NULL," +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "FOREIGN KEY(${MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN}) REFERENCES ${CharacterEntity.TABLE_NAME}(${ProfileEntity.ID_COLUMN_NAME}) ON UPDATE NO ACTION ON DELETE CASCADE," +
                "FOREIGN KEY(${MythicPlusScoreBaseEntity.SEASON_ID_COLUMN}) REFERENCES ${SeasonEntity.TABLE_NAME}(id))"


    private fun createMythicPlusRoleScoreEntityTable(database: SupportSQLiteDatabase) {
        val createMythicPlusRoleScoreEntityTableQuery =
            buildCreateMythicPlusRoleScoreEntityTableQuery()
        database.execSQL(createMythicPlusRoleScoreEntityTableQuery)
    }

    private fun buildCreateMythicPlusRoleScoreEntityTableQuery(): String =
        "CREATE TABLE IF NOT EXISTS ${MythicPlusRoleScoreEntity.TABLE_NAME}(" +
                "${MythicPlusRoleScoreEntity.ROLE_COLUMN} TEXT NOT NULL," +
                "${MythicPlusScoreBaseEntity.SCORE_COLUMN} REAL NOT NULL," +
                "${MythicPlusScoreBaseEntity.SEASON_ID_COLUMN} INTEGER NOT NULL," +
                "${MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN} INTEGER NOT NULL," +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "FOREIGN KEY(${MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN}) REFERENCES ${CharacterEntity.TABLE_NAME}(${ProfileEntity.ID_COLUMN_NAME}) ON UPDATE NO ACTION ON DELETE CASCADE," +
                "FOREIGN KEY(${MythicPlusScoreBaseEntity.SEASON_ID_COLUMN}) REFERENCES ${SeasonEntity.TABLE_NAME}(id))"

    private fun saveMythicPlusScoresInNewTables(
        database: SupportSQLiteDatabase,
        scores: List<CharacterMythicPlusScoresContainer>
    ) {
        scores.forEach { (characterId, seasonId, overallScore, roleScores) ->
            saveOverallMythicPlusScore(database, overallScore, seasonId, characterId)
            saveRoleMythicPlusScore(database, roleScores, seasonId, characterId)
        }
    }

    private fun saveOverallMythicPlusScore(
        database: SupportSQLiteDatabase,
        overallScore: MythicPlusScore,
        seasonId: Long,
        characterId: Long
    ) {
        database.insert(
            MythicPlusOverallScoreEntity.TABLE_NAME,
            SQLiteDatabase.CONFLICT_FAIL,
            contentValuesOf(
                MythicPlusScoreBaseEntity.SCORE_COLUMN to overallScore,
                MythicPlusScoreBaseEntity.SEASON_ID_COLUMN to seasonId,
                MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN to characterId
            )
        )
    }

    private fun saveRoleMythicPlusScore(
        database: SupportSQLiteDatabase,
        roleScores: Map<String, MythicPlusScore>,
        seasonId: Long,
        characterId: Long
    ) {
        roleScores.forEach { (roleValue, score) ->
            database.insert(
                MythicPlusRoleScoreEntity.TABLE_NAME,
                SQLiteDatabase.CONFLICT_FAIL,
                contentValuesOf(
                    MythicPlusRoleScoreEntity.ROLE_COLUMN to roleValue,
                    MythicPlusScoreBaseEntity.SCORE_COLUMN to score,
                    MythicPlusScoreBaseEntity.SEASON_ID_COLUMN to seasonId,
                    MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN to characterId
                )
            )
        }
    }

    private data class CharacterMythicPlusScoresContainer(
        val characterId: Long,
        val seasonId: Long,
        val overallScore: MythicPlusScore,
        val roleScores: Map<String, MythicPlusScore>
    )
}

