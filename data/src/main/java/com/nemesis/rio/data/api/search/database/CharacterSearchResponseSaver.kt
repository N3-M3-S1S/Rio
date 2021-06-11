package com.nemesis.rio.data.api.search.database

import com.nemesis.rio.data.api.search.CharacterSearchResponse
import com.nemesis.rio.data.mplus.ranks.serialization.MythicPlusRanksContainer
import com.nemesis.rio.data.mplus.scores.serialization.MythicPlusScoresContainer
import com.nemesis.rio.data.profile.database.ProfileSaver
import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.progress.RaidProgress

class CharacterSearchResponseSaver(
    private val profileSaver: ProfileSaver<Character>,
    private val mythicPlusScoresSaver: ProfileProgressSaver<List<MythicPlusScoresContainer>>,
    private val mythicPlusRanksSaver: ProfileProgressSaver<Map<MythicPlusRanksScope, MythicPlusRanksContainer>>,
    private val mythicPlusRunsSaver: ProfileProgressSaver<List<MythicPlusRun>>,
    private val raidAchievementsSaver: ProfileProgressSaver<Map<Raid, List<RaidAchievement>>>,
    private val raidProgressSaver: ProfileProgressSaver<Map<Raid, RaidProgress>>,
) {

    suspend fun saveOrUpdateResponseContent(response: CharacterSearchResponse) {
        with(response) {
            val characterId = profileSaver.saveOrUpdateProfileAndCacheID(response.character)
            mythicPlusScoresSaver.saveOrUpdate(mythicPlusScores, characterId)
            mythicPlusRanksSaver.saveOrUpdate(mythicPlusRanks, characterId)
            mythicPlusRunsSaver.saveOrUpdate(mythicPlusRuns, characterId)
            raidAchievementsSaver.saveOrUpdate(raidAchievements, characterId)
            raidProgressSaver.saveOrUpdate(raidProgress, characterId)
        }
    }
}
