package com.nemesis.rio.data.mplus.ranks.database

import com.nemesis.rio.data.AppDatabaseTest
import com.nemesis.rio.data.profile.database.createTestCharacterInDatabase
import com.nemesis.rio.domain.mplus.ranks.MythicPlusClassRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusOverallRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec
import com.nemesis.rio.domain.ranks.Ranks
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MythicPlusRanksDaoTest : AppDatabaseTest() {
    private val mythicPlusRanksDao = appDatabase.mythicPlusRanksDao

    @Test
    fun saveAndGetSpecRanks() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val scope = MythicPlusRanksScope.FACTION
        val expectedSpecRanks = mapOf(
            Spec.WINDWALKER to Ranks(3, 2, 1),
            Spec.BREWMASTER to Ranks(300, 200, 100)
        )

        mythicPlusRanksDao.saveMythicPlusSpecRanksEntities(
            expectedSpecRanks.toSpecRanksEntities(
                scope,
                characterID
            )
        )

        val specRanks =
            mythicPlusRanksDao.getMythicPlusSpecRanksEntities(scope, characterID).toSpecRanks()
        assertEquals(expectedSpecRanks, specRanks)
    }

    @Test
    fun saveAndGetClassRanks() = runBlocking {
        val ranksType = BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_CLASS
        val characterID = createTestCharacterInDatabase()
        val scope = MythicPlusRanksScope.GLOBAL
        val expectedClassRanks = MythicPlusClassRanks(
            Ranks(300, 200, 100),
            mapOf(Role.HEALER to Ranks(6, 5, 4))
        )

        mythicPlusRanksDao.saveMythicPlusRanksEntity(
            expectedClassRanks.ranksForClass.toRanksEntity(
                ranksType,
                scope,
                characterID
            )
        )
        mythicPlusRanksDao.saveMythicPlusRoleRanksEntities(
            expectedClassRanks.ranksForClassRoles.toRoleRanksEntities(
                ranksType,
                scope,
                characterID
            )
        )

        val classRanks =
            mythicPlusRanksDao.getMythicPlusRanksEntity(ranksType, scope, characterID)?.ranks
        val classRoleRanks =
            mythicPlusRanksDao.getMythicPlusRoleRanksEntities(ranksType, scope, characterID)
                .toRoleRanks()
        assertNotNull(classRanks)
        assertTrue(classRoleRanks.isNotEmpty())

        val classRanksFromDatabase = MythicPlusClassRanks(classRanks, classRoleRanks)
        assertEquals(expectedClassRanks, classRanksFromDatabase)
    }

    @Test
    fun saveAndGetOverallRanks() = runBlocking {
        val ranksType = BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_OVERALL
        val characterID = createTestCharacterInDatabase()
        val scope = MythicPlusRanksScope.GLOBAL
        val expectedClassRanks = MythicPlusOverallRanks(
            Ranks(300, 200, 100),
            mapOf(
                Role.TANK to Ranks(6, 5, 4),
                Role.DAMAGE_DEALER to Ranks(9999, 99999, 99999)
            )
        )

        mythicPlusRanksDao.saveMythicPlusRanksEntity(
            expectedClassRanks.overallRanks.toRanksEntity(
                ranksType,
                scope,
                characterID
            )
        )
        mythicPlusRanksDao.saveMythicPlusRoleRanksEntities(
            expectedClassRanks.overallRoleRanks.toRoleRanksEntities(
                ranksType,
                scope,
                characterID
            )
        )

        val overallRanks =
            mythicPlusRanksDao.getMythicPlusRanksEntity(ranksType, scope, characterID)?.ranks
        val overallRoleRanks =
            mythicPlusRanksDao.getMythicPlusRoleRanksEntities(ranksType, scope, characterID)
                .toRoleRanks()
        assertNotNull(overallRanks)
        assertTrue(overallRoleRanks.isNotEmpty())

        val overallRanksFromDatabase = MythicPlusOverallRanks(overallRanks, overallRoleRanks)
        assertEquals(expectedClassRanks, overallRanksFromDatabase)
    }

    @Test
    fun deleteRanks() = runBlocking {
        val characterID = createTestCharacterInDatabase()
        val scope = MythicPlusRanksScope.GLOBAL
        val expectedRanks = mapOf(Spec.RETRIBUTION to Ranks(1, 2, 3))

        mythicPlusRanksDao.saveMythicPlusSpecRanksEntities(
            expectedRanks.toSpecRanksEntities(
                scope,
                characterID
            )
        )

        val ranks =
            mythicPlusRanksDao.getMythicPlusSpecRanksEntities(scope, characterID).toSpecRanks()
        assertEquals(expectedRanks, ranks)

        mythicPlusRanksDao.deleteAll(characterID)
        val ranksAfterDelete =
            mythicPlusRanksDao.getMythicPlusSpecRanksEntities(scope, characterID).toSpecRanks()
        assertTrue(ranksAfterDelete.isEmpty())
    }

}
