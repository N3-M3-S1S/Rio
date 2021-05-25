package com.nemesis.rio.data.mplus.scores.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.character.Character

class MythicPlusScoresDatabaseSource(
    private val scoresDao: MythicPlusScoresDao,
    private val profileIDProvider: ProfileIDProvider<Character>,
) : MythicPlusScoresSource {

    override suspend fun getScoresForSeason(
        character: Character,
        season: Season,
    ): MythicPlusScores {
        return profileIDProvider.withProfileID(character) {
            scoresDao.getScoresPojoForSeason(season, it)?.toMythicPlusScores()
                ?: zeroScores()
        }
    }

    override suspend fun getExpansionsWithScores(character: Character): List<Expansion> =
        profileIDProvider.withProfileID(character, scoresDao::getExpansionsWithScores)

    override suspend fun getSeasonsWithScores(
        character: Character,
        expansion: Expansion
    ): List<Season> =
        profileIDProvider.withProfileID(character) { scoresDao.getSeasonsWithScores(it, expansion) }

    private fun zeroScores() = MythicPlusScores(0f, emptyMap())
}
