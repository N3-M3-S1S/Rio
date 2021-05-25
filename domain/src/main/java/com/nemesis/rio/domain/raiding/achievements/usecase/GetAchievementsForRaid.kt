package com.nemesis.rio.domain.raiding.achievements.usecase

import com.nemesis.rio.domain.profile.character.Character
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import com.nemesis.rio.domain.raiding.source.CharacterRaidingSource

class GetAchievementsForRaid(private val characterRaidingSource: CharacterRaidingSource) {

    suspend operator fun invoke(character: Character, raid: Raid): List<RaidAchievement> =
        characterRaidingSource.getRaidAchievementsForRaid(character, raid)
}
