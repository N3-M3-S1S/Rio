package com.nemesis.rio.presentation.profile.overview.character.overall

import com.nemesis.rio.domain.mplus.scores.usecase.GetHighestMythicPlusScoreForSeason
import com.nemesis.rio.domain.mplus.seasons.usecase.GetCurrentSeason
import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.profile.update.usecase.GetProfileLastUpdateDateTime
import com.nemesis.rio.domain.raiding.achievements.bestAchievementOrNull
import com.nemesis.rio.domain.raiding.achievements.usecase.GetAchievementsForRaid
import com.nemesis.rio.domain.raiding.progress.bestProgress
import com.nemesis.rio.domain.raiding.progress.usecase.GetProgressForRaid
import com.nemesis.rio.domain.raiding.usecase.GetCurrentRaid

class CharacterOverallDataFactory(
    private val getCurrentSeason: GetCurrentSeason,
    private val getHighestMythicPlusScoreForSeason: GetHighestMythicPlusScoreForSeason,
    private val getCurrentRaid: GetCurrentRaid,
    private val getBestKillsForRaid: GetProgressForRaid,
    private val getAchievementsForRaid: GetAchievementsForRaid,
    private val getProfileLastRefreshDateTime: GetProfileLastUpdateDateTime<Character>,
) {

    suspend fun getCharacterOverallData(character: Character): CharacterOverallData {
        val mythicPlusData = getMythicPlusDataForCurrentSeason(character)
        val raidingData = getRaidingDataForCurrentRaid(character)
        return CharacterOverallData(
            character,
            mythicPlusData,
            raidingData,
            getProfileLastRefreshDateTime(character)
        )
    }

    private suspend fun getMythicPlusDataForCurrentSeason(character: Character): CharacterOverallMythicPlusData {
        val currentSeason = getCurrentSeason()
        val highestMythicPlusScoreForCurrentSeason =
            getHighestMythicPlusScoreForSeason(character, currentSeason)
        return CharacterOverallMythicPlusData(currentSeason, highestMythicPlusScoreForCurrentSeason)
    }

    private suspend fun getRaidingDataForCurrentRaid(character: Character): CharacterOverallRaidingData {
        val currentRaid = getCurrentRaid()
        val bestCurrentRaidAchievement = getAchievementsForRaid(character, currentRaid).bestAchievementOrNull()
        val (difficulty, kills) = getBestKillsForRaid(character, currentRaid).bestProgress()
        return CharacterOverallRaidingData(currentRaid, difficulty, kills, bestCurrentRaidAchievement)
    }
}
