package com.nemesis.rio.presentation.profile.overview.character.overall

import com.nemesis.rio.domain.mplus.scores.color.usecase.GetHexColorForMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetOverallMythicPlusScore
import com.nemesis.rio.domain.mplus.seasons.usecase.GetCurrentSeason
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.update.usecase.GetProfileLastUpdateDateTime
import com.nemesis.rio.domain.raiding.achievements.bestAchievementOrNull
import com.nemesis.rio.domain.raiding.achievements.usecase.GetAchievementsForRaid
import com.nemesis.rio.domain.raiding.progress.bestProgress
import com.nemesis.rio.domain.raiding.progress.usecase.GetProgressForRaid
import com.nemesis.rio.domain.raiding.usecase.GetCurrentRaid
import com.nemesis.rio.presentation.utils.increaseForegroundHexColorBrightnessToWCAGAAStandard

class CharacterOverallDataFactory(
    private val getCurrentSeason: GetCurrentSeason,
    private val getOverallMythicPlusScore: GetOverallMythicPlusScore,
    private val getHexColorForMythicPlusScore: GetHexColorForMythicPlusScore,
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
        val overallScoreForCurrentSeason =
            getOverallMythicPlusScore(character, currentSeason)
        val scoreColor =
            getHexColorForMythicPlusScore(overallScoreForCurrentSeason, currentSeason)
        return CharacterOverallMythicPlusData(
            currentSeason,
            overallScoreForCurrentSeason,
            scoreColor
        )
    }

    private suspend fun getRaidingDataForCurrentRaid(character: Character): CharacterOverallRaidingData {
        val currentRaid = getCurrentRaid()
        val bestCurrentRaidAchievement =
            getAchievementsForRaid(character, currentRaid).bestAchievementOrNull()
        val (difficulty, kills) = getBestKillsForRaid(character, currentRaid).bestProgress()
        return CharacterOverallRaidingData(
            currentRaid,
            difficulty,
            kills,
            bestCurrentRaidAchievement
        )
    }
}
