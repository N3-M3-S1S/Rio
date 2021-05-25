package com.nemesis.rio.domain.raiding.source

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.progress.RaidProgress

interface CharacterRaidingSource {
    suspend fun getProgressForRaid(character: Character, raid: Raid): RaidProgress
    suspend fun getAllRaidProgress(character: Character): Map<Raid, RaidProgress>
    suspend fun getRaidAchievementsForRaid(character: Character, raid: Raid): List<RaidAchievement>
    suspend fun getAllRaidAchievements(character: Character): Map<Raid, List<RaidAchievement>>
}
