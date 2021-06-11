package com.nemesis.rio.domain.mplus.scores.usecase

import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.MythicPlusScoresSource
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Role

class GetRoleMythicPlusScores(private val scoresSource: MythicPlusScoresSource) {

    suspend operator fun invoke(character: Character, season: Season): Map<Role, MythicPlusScore> =
        scoresSource.getRoleScores(character, season)
}
