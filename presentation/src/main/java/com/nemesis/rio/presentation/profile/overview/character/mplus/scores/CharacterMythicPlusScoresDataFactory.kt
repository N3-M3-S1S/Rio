package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import androidx.annotation.StringRes
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.usecase.GetHexColorForMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetOverallMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetRoleMythicPlusScores
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.character.attributes.nameResId
import splitties.resources.appStr

class CharacterMythicPlusScoresDataFactory(
    private val getOverallMythicPlusScore: GetOverallMythicPlusScore,
    private val getRoleMythicPlusScores: GetRoleMythicPlusScores,
    private val getHexColorForMythicPlusScore: GetHexColorForMythicPlusScore
) {

    suspend fun getScoresData(
        character: Character,
        selectedSeason: Season,
        selectedExpansion: Expansion
    ): CharacterMythicPlusScoresData {
        val scoreItemsForSelectedSeason = mutableListOf<CharacterMythicPlusScoreItem>()
        val overallScore = getOverallMythicPlusScore(character, selectedSeason)
        val roleScores = getRoleMythicPlusScores(character, selectedSeason)

        if (overallScore > roleScores.maxOf { (_, score) -> score }) {
            val overallScoreItem = createMythicPlusScoreItem(
                R.string.character_mplus_score_overall,
                overallScore,
                selectedSeason
            )
            scoreItemsForSelectedSeason.add(overallScoreItem)
        }

        roleScores.mapTo(scoreItemsForSelectedSeason) { (role, score) ->
            createMythicPlusScoreItem(role.nameResId, score, selectedSeason)
        }

        scoreItemsForSelectedSeason.sortByDescending(CharacterMythicPlusScoreItem::score)

        return CharacterMythicPlusScoresData(
            selectedExpansion,
            selectedSeason,
            scoreItemsForSelectedSeason
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
