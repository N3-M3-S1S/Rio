package com.nemesis.rio.data.mplus.ranks.database

import com.nemesis.rio.data.mplus.ranks.serialization.MythicPlusRanksContainer
import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.mplus.ranks.MythicPlusClassRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusOverallRanks
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.ranks.MythicPlusSpecRanks

class MythicPlusRanksSaver(private val mythicPlusRanksDao: MythicPlusRanksDao) :
    ProfileProgressSaver<Map<MythicPlusRanksScope, MythicPlusRanksContainer>> {

    override suspend fun saveOrUpdate(
        progress: Map<MythicPlusRanksScope, MythicPlusRanksContainer>,
        profileId: Long,
    ) {
        mythicPlusRanksDao.deleteAll(profileId)
        progress.forEach { (scope, ranks) ->
            with(ranks) {
                saveSpecRanks(specRanks, scope, profileId)
                saveClassRanks(classRanks, scope, profileId)
                saveOverallRanks(overallRanks, scope, profileId)
            }
        }
    }

    private suspend fun saveSpecRanks(
        ranks: MythicPlusSpecRanks,
        scope: MythicPlusRanksScope,
        characterId: Long,
    ) {
        mythicPlusRanksDao.saveMythicPlusSpecRanksEntities(
            ranks.toSpecRanksEntities(scope, characterId)
        )
    }

    private suspend fun saveClassRanks(
        ranks: MythicPlusClassRanks,
        scope: MythicPlusRanksScope,
        characterId: Long,
    ) {
        val classRanksEntity =
            ranks.ranksForClass.toRanksEntity(
                BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_CLASS,
                scope,
                characterId
            )
        mythicPlusRanksDao.saveMythicPlusRanksEntity(classRanksEntity)
        val classRoleRanksEntities = ranks.ranksForClassRoles.toRoleRanksEntities(
            BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_CLASS,
            scope,
            characterId
        )
        mythicPlusRanksDao.saveMythicPlusRoleRanksEntities(classRoleRanksEntities)
    }

    private suspend fun saveOverallRanks(
        ranks: MythicPlusOverallRanks,
        scope: MythicPlusRanksScope,
        characterId: Long,
    ) {
        val overallRanksEntity =
            ranks.overallRanks.toRanksEntity(
                BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_OVERALL,
                scope,
                characterId
            )
        mythicPlusRanksDao.saveMythicPlusRanksEntity(overallRanksEntity)
        val overallRoleRanksEntities = ranks.overallRoleRanks.toRoleRanksEntities(
            BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_OVERALL,
            scope,
            characterId
        )
        mythicPlusRanksDao.saveMythicPlusRoleRanksEntities(overallRoleRanksEntities)
    }
}
