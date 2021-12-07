package com.nemesis.rio.domain.mplus.scores

import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.domain.mplus.seasons.Season
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.profile.character.attributes.Role
import com.nemesis.rio.domain.profile.character.attributes.Spec

interface MythicPlusScoresSource {
    suspend fun getOverallScore(character: Character, season: Season): MythicPlusScore
    suspend fun getRoleScores(character: Character, season: Season): Map<Role, MythicPlusScore>
    suspend fun getSpecScores(character: Character, season: Season): Map<Spec, MythicPlusScore>
    suspend fun getSeasonsWithScores(character: Character): Map<Expansion, List<Season>>
}
