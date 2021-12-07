package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec

class MythicPlusScoresDatabaseSource(
    private val scoresDao: MythicPlusScoresDao,
    private val profileIDProvider: ProfileIDProvider<Character>,
) : MythicPlusScoresSource {

    override suspend fun getSeasonsWithScores(
        character: Character
    ): Map<Expansion, List<Season>> =
        profileIDProvider.withProfileID(character) { scoresDao.getSeasonsWithScores(it) }

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
        scoresDao.getRoleScoreEntities(it, season).toRoleScores()
    }

    override suspend fun getSpecScores(
        character: Character,
        season: Season
    ): Map<Spec, MythicPlusScore> = profileIDProvider.withProfileID(character) {
        scoresDao.getSpecScoreEntities(it, season).toSpecScores()
    }
}
