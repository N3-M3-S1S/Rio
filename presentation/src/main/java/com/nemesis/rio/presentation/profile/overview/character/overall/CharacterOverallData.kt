package com.nemesis.rio.presentation.profile.overview.character.overall

import com.nemesis.rio.domain.profile.Character
import kotlinx.datetime.Instant

data class CharacterOverallData(
    val character: Character,
    val mythicPlusData: CharacterOverallMythicPlusData,
    val raidingData: CharacterOverallRaidingData,
    val lastUpdate: Instant
)
