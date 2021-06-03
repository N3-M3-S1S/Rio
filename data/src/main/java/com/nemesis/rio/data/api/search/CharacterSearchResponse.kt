package com.nemesis.rio.data.api.search

import com.nemesis.rio.data.api.search.serialization.CharacterSearchResponseDeserializer
import com.nemesis.rio.data.mplus.ranks.serialization.MythicPlusRanksContainer
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.scores.MythicPlusScores
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable(with = CharacterSearchResponseDeserializer::class)
data class CharacterSearchResponse(
    @Transient val character: Character,
    @Transient val mythicPlusSeasonApiValueToScore: Map<String, MythicPlusScores>,
    @Transient val mythicPlusRanks: Map<MythicPlusRanksScope, MythicPlusRanksContainer>,
    @Transient val mythicPlusRuns: List<MythicPlusRun>,
    @Transient val raidAchievements: Map<Raid, List<RaidAchievement>>,
    @Transient val raidProgress: Map<Raid, RaidProgress>,
)
