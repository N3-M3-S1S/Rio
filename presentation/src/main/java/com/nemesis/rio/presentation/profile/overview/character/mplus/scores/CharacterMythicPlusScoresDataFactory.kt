package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import com.airbnb.epoxy.IdUtils
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetOverallMythicPlusScore
import com.nemesis.rio.domain.mplus.scores.usecase.GetRoleMythicPlusScores
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoreItem
import com.nemesis.rio.presentation.profile.character.attributes.nameResId

class CharacterMythicPlusScoresDataFactory(
    private val getOverallMythicPlusScore: GetOverallMythicPlusScore,
    private val getRoleMythicPlusScores: GetRoleMythicPlusScores
) {

    suspend fun getScoresData(
        character: Character,
        selectedSeason: Season,
        selectedExpansion: Expansion
    ): CharacterMythicPlusScoresData {
        val mythicPlusScoreItemsForSelectedSeason = mutableListOf<MythicPlusScoreItem>()
        val overallScore = getOverallMythicPlusScore(character, selectedSeason)
        val roleScores = getRoleMythicPlusScores(character, selectedSeason)

        if (overallScore > roleScores.maxOf { (_, score) -> score }) {
            mythicPlusScoreItemsForSelectedSeason.add(createOverallMythicPlusScoreItem(overallScore))
        }

        roleScores.mapTo(mythicPlusScoreItemsForSelectedSeason) { (role, score) ->
            createRoleMythicPlusScoreItem(role, score)
        }

        mythicPlusScoreItemsForSelectedSeason.sortByDescending(MythicPlusScoreItem::score)

        return CharacterMythicPlusScoresData(
            selectedExpansion,
            selectedSeason,
            mythicPlusScoreItemsForSelectedSeason
        )
    }


    private fun createOverallMythicPlusScoreItem(overallMythicPlusScore: MythicPlusScore): MythicPlusScoreItem =
        MythicPlusScoreItem(
            IdUtils.hashString64Bit("overall"),
            R.string.character_mplus_score_overall,
            overallMythicPlusScore
        )

    private fun createRoleMythicPlusScoreItem(role: Role, score: MythicPlusScore) =
        MythicPlusScoreItem(
            IdUtils.hashString64Bit(role.name),
            role.nameResId,
            score
        )

}
