package com.nemesis.rio.data.database.migrations

import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.nemesis.rio.data.database.AppDatabase
import com.nemesis.rio.data.mplus.scores.database.MythicPlusOverallScoreEntity
import com.nemesis.rio.data.mplus.scores.database.MythicPlusRoleScoreEntity
import com.nemesis.rio.data.mplus.scores.database.MythicPlusScoreBaseEntity
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.utils.searchEnumByName
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("ClassName")
class Migration_1_2_Test {
    private val testDatabaseName = "testDatabase"

    @Rule
    @JvmField
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun test() {
        var database = helper.createDatabase(testDatabaseName, 1)

        val testCharacterId = createTestCharacterInDatabase(database)
        val testSeasonId = createTestSeasonInDatabase(database)

        val expectedOverallScore = 1234f
        val expectedRoleScores =
            mapOf(Role.TANK to 123.4f, Role.HEALER to 1112.222f, Role.DAMAGE_DEALER to 123f)

        createMythicPlusScoresInDatabaseVersion1(
            database,
            testCharacterId,
            testSeasonId,
            expectedOverallScore,
            expectedRoleScores
        )

        database = helper.runMigrationsAndValidate(testDatabaseName, 2, true, Migration_1_2)

        val overallScoreAfterMigration =
            getOverallMythicPlusScoreFromDatabaseVersion2(database, testSeasonId, testCharacterId)
        assertEquals(expectedOverallScore, overallScoreAfterMigration)

        val roleScoresAfterMigration =
            getRoleMythicPlusScoresFromDatabaseVersion2(database, testSeasonId, testCharacterId)
        assertEquals(roleScoresAfterMigration, expectedRoleScores)
    }

    private fun createTestCharacterInDatabase(
        database: SupportSQLiteDatabase,
        name: String = "testCharacter"
    ): Long {
        return database.insert(
            CharacterEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_FAIL, contentValuesOf(
                "name" to name,
                "region" to "Region",
                "faction" to "Faction",
                "url" to "Url",
                "realm" to "Realm",
                "imageUrl" to "ImageUrl",
                "lastRefreshDateTime" to "1"
            )
        )
    }

    private fun createTestSeasonInDatabase(
        database: SupportSQLiteDatabase,
        seasonApiValue: String = "testApiValue"
    ): Long {
        return database.insert(
            SeasonEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_FAIL, contentValuesOf(
                "api_value" to seasonApiValue,
                "name" to "name",
                "expansion" to 1
            )
        )
    }

    private fun createMythicPlusScoresInDatabaseVersion1(
        database: SupportSQLiteDatabase,
        characterId: Long,
        seasonId: Long,
        overallScore: Float,
        roleScores: Map<Role, MythicPlusScore>
    ) {
        val overallScoreParentEntityId =
            createMythicPlusOverallScoreParentEntity(database, overallScore, seasonId, characterId)
        createMythicPlusRoleScoreChildEntities(database, overallScoreParentEntityId, roleScores)
    }

    private fun createMythicPlusOverallScoreParentEntity(
        database: SupportSQLiteDatabase,
        overallScore: MythicPlusScore,
        seasonId: Long,
        characterId: Long
    ): Long {
        return database.insert(
            "MythicPlusOverallScoreParentEntity", SQLiteDatabase.CONFLICT_FAIL, contentValuesOf(
                "overallScore" to overallScore,
                "seasonId" to seasonId,
                ProfileEntity.ID_COLUMN_NAME to characterId
            )
        )
    }

    private fun createMythicPlusRoleScoreChildEntities(
        database: SupportSQLiteDatabase,
        overallScoreId: Long,
        roleScores: Map<Role, MythicPlusScore>
    ) {
        roleScores.forEach { (role, score) ->
            database.insert(
                "MythicPlusRoleScoreChildEntity", SQLiteDatabase.CONFLICT_FAIL, contentValuesOf(
                    "role" to role.toString(),
                    "score" to score,
                    "overallScoreId" to overallScoreId
                )
            )
        }
    }

    private fun getOverallMythicPlusScoreFromDatabaseVersion2(
        database: SupportSQLiteDatabase,
        seasonId: Long,
        characterId: Long
    ): MythicPlusScore {
        val selectOverallScoreQuery = buildSelectOverallScoreQuery(seasonId, characterId)
        return database.query(selectOverallScoreQuery)
            .use { cursor ->
                cursor.moveToFirst()
                cursor.getFloat(cursor.getColumnIndex(MythicPlusScoreBaseEntity.SCORE_COLUMN))
            }
    }

    private fun buildSelectOverallScoreQuery(
        seasonId: Long,
        characterId: Long
    ): SupportSQLiteQuery = SupportSQLiteQueryBuilder
        .builder(MythicPlusOverallScoreEntity.TABLE_NAME)
        .selection(
            "${MythicPlusScoreBaseEntity.SEASON_ID_COLUMN} = ? AND ${MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN} = ?",
            arrayOf(seasonId, characterId)
        ).create()

    private fun getRoleMythicPlusScoresFromDatabaseVersion2(
        database: SupportSQLiteDatabase,
        seasonId: Long,
        characterId: Long
    ): Map<Role, MythicPlusScore> {
        val roleScores = mutableMapOf<Role, MythicPlusScore>()
        val selectRoleScoresQuery = buildSelectRoleScoresQuery(seasonId, characterId)

        database.query(selectRoleScoresQuery).use { cursor ->
            while (cursor.moveToNext()) {
                val roleValue =
                    cursor.getString(cursor.getColumnIndex(MythicPlusRoleScoreEntity.ROLE_COLUMN))
                val role = searchEnumByName<Role>(roleValue)!!
                val score =
                    cursor.getFloat(cursor.getColumnIndex(MythicPlusScoreBaseEntity.SCORE_COLUMN))
                roleScores[role] = score
            }
        }

        return roleScores
    }

    private fun buildSelectRoleScoresQuery(
        seasonId: Long,
        characterId: Long
    ): SupportSQLiteQuery = SupportSQLiteQueryBuilder
        .builder(MythicPlusRoleScoreEntity.TABLE_NAME)
        .selection(
            "${MythicPlusScoreBaseEntity.SEASON_ID_COLUMN} = ? AND ${MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN} = ?",
            arrayOf(seasonId, characterId)
        ).create()


}
