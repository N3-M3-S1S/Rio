package com.nemesis.rio.presentation.profile.overview.character.raiding

import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.domain.raiding.achievements.usecase.GetAllRaidAchievements
import com.nemesis.rio.domain.raiding.progress.usecase.GetAllRaidProgress
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressItem
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressListData

class CharacterRaidProgressListDataFactory(
    private val getAllRaidProgress: GetAllRaidProgress,
    private val getAllRaidAchievements: GetAllRaidAchievements
) {

    suspend fun getCharacterRaidProgressListData(character: Character): RaidProgressListData {
        val raidProgressItems = mutableListOf<RaidProgressItem>()
        val raidProgress = getAllRaidProgress(character)
        val raidAchievements = getAllRaidAchievements(character).toMutableMap()

        raidProgress.mapTo(raidProgressItems) { (raid, progress) ->
            val achievementsForRaid = raidAchievements.getOrElse(raid, ::emptyList)
            raidAchievements.remove(raid)
            RaidProgressItem(raid, progress, achievementsForRaid)
        }

        raidAchievements.mapTo(raidProgressItems) { (raid, achievements) -> // create progress items for raids without progress but with achievements (this happens if achievement was gotten on alt character)
            RaidProgressItem(raid, raidAchievements = achievements)
        }

        return raidProgressItems.groupBy { it.raid.expansion }
    }
}
