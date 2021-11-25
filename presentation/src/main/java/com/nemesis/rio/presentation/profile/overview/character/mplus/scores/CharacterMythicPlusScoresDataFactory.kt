package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import androidx.annotation.StringRes
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.usecase.GetHexColorForMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetOverallMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetRoleMythicPlusScores
import com.nemesis.rio.domain.mplus.scores.usecase.GetSpecMythicPlusScores
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoresType
import com.nemesis.rio.presentation.profile.character.attributes.stringResId
import splitties.resources.appStr

class CharacterMythicPlusScoresDataFactory(
    private val getOverallMythicPlusScore: GetOverallMythicPlusScore,
    private val getRoleMythicPlusScores: GetRoleMythicPlusScores,
    private val getSpecMythicPlusScores: GetSpecMythicPlusScores,
    private val getHexColorForMythicPlusScore: GetHexColorForMythicPlusScore
) {

    suspend fun getScoresData(
        character: Character,
        selectedScoresType: MythicPlusScoresType,
        selectedSeason: Season,
    ): CharacterMythicPlusScoresData {
        val scoreItems = when (selectedScoresType) {
            MythicPlusScoresType.ROLE_SCORES -> getRoleScoreItems(character, selectedSeason)
            MythicPlusScoresType.SPEC_SCORES -> getSpecScoreItems(character, selectedSeason)
        }.toMutableList()

        val overallScore = getOverallMythicPlusScore(character, selectedSeason)
        val scoreItemWithMaximumScore =
            scoreItems.maxOfOrNull { (_, score) -> score }

        if (scoreItemWithMaximumScore == null || overallScore > scoreItemWithMaximumScore) {
            val overallScoreItem = getOverallScoreItem(overallScore, selectedSeason)
            scoreItems.add(overallScoreItem)
        }

        scoreItems.sortByDescending(CharacterMythicPlusScoreItem::score)

        return CharacterMythicPlusScoresData(
            selectedScoresType,
            selectedSeason,
            scoreItems
        )
    }

    private suspend fun getOverallScoreItem(overallScore: MythicPlusScore, season: Season) =
        createMythicPlusScoreItem(
            R.string.character_mplus_score_overall,
            overallScore,
            season
        )

    private suspend fun getRoleScoreItems(
        character: Character,
        season: Season
    ): List<CharacterMythicPlusScoreItem> =
        getRoleMythicPlusScores(
            character,
            season
        ).map { (role, score) ->
            createMythicPlusScoreItem(
                role.stringResId,
                score,
                season
            )
        }

    private suspend fun getSpecScoreItems(
        character: Character,
        season: Season
    ): List<CharacterMythicPlusScoreItem> =
        getSpecMythicPlusScores(
            character,
            season
        ).map { (spec, score) ->
            createMythicPlusScoreItem(
                spec.stringResId,
                score,
                season
            )
        }

    private suspend fun createMythicPlusScoreItem(
        @StringRes
        titleResId: Int,
        score: MythicPlusScore,
        season: Season
    ): CharacterMythicPlusScoreItem {
        val scoreColor = getHexColorForMythicPlusScore(score, season)
        return CharacterMythicPlusScoreItem(appStr(titleResId), score, scoreColor)
    }
}
