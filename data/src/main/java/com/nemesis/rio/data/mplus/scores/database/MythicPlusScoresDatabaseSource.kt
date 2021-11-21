package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Role

class MythicPlusScoresDatabaseSource(
    private val scoresDao: MythicPlusScoresDao,
    private val profileIDProvider: ProfileIDProvider<Character>,
) : MythicPlusScoresSource {

    override suspend fun getExpansionsWithScores(character: Character): List<Expansion> =
        profileIDProvider.withProfileID(character, scoresDao::getExpansionsWithScores)

    override suspend fun getSeasonsWithScores(
        character: Character,
        expansion: Expansion
    ): List<Season> =
        profileIDProvider.withProfileID(character) { scoresDao.getSeasonsWithScores(it, expansion) }

    override suspend fun getOverallScore(
        character: Character,
        season: Season
    ): MythicPlusScore = profileIDProvider.withProfileID(character) {
        scoresDao.getOverallScore(it, season) ?: 0F
    }

    override suspend fun getRoleScores(
        character: Character,
        season: Season
    ): Map<Role, MythicPlusScore> = profileIDProvider.withProfileID(character) {
        scoresDao.getRoleScoresEntities(it, season).toRoleScores()
    }
}
