package com.nemesis.rio.data.mplus.ranks.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.domain.mplus.ranks.*
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.ranks.Ranks

class MythicPlusRanksDatabaseSource(
    private val ranksDao: MythicPlusRanksDao,
    private val profileIDProvider: ProfileIDProvider<Character>,
) : MythicPlusRanksSource {

    override suspend fun getClassRanksForCurrentSeason(
        character: Character,
        scope: MythicPlusRanksScope,
    ): MythicPlusClassRanks = profileIDProvider.withProfileID(character) {
        getClassOverallRanks(
            BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_CLASS,
            scope,
            it,
            ::MythicPlusClassRanks
        )
    }

    override suspend fun getSpecRanksForCurrentSeason(
        character: Character,
        scope: MythicPlusRanksScope,
    ): MythicPlusSpecRanks = profileIDProvider.withProfileID(character) {
        ranksDao.getMythicPlusSpecRanksEntities(scope, it).toSpecRanks()
    }

    override suspend fun getOverallRanksForCurrentSeason(
        character: Character,
        scope: MythicPlusRanksScope,
    ): MythicPlusOverallRanks = profileIDProvider.withProfileID(character) {
        getClassOverallRanks(
            BaseClassOverallMythicPlusRanksEntity.RANKS_TYPE_OVERALL,
            scope,
            it,
            ::MythicPlusOverallRanks
        )
    }

    override suspend fun characterHasRanksForCurrentSeason(character: Character): Boolean =
        profileIDProvider.withProfileID(character, ranksDao::characterHasAnyMythicPlusRanksEntities)

    private suspend fun <T> getClassOverallRanks(
        ranksType: Int,
        ranksScope: MythicPlusRanksScope,
        characterID: Long,
        block: (ranks: Ranks, roleRanks: Map<Role, Ranks>) -> T
    ): T {
        val ranks =
            ranksDao.getMythicPlusRanksEntity(ranksType, ranksScope, characterID)?.ranks
                ?: zeroRanks()
        val roleRanks =
            ranksDao.getMythicPlusRoleRanksEntities(ranksType, ranksScope, characterID)
                .toRoleRanks()
        return block(ranks, roleRanks)
    }
}

fun zeroRanks() = Ranks(0, 0, 0)
