package com.nemesis.rio.presentation.profile.overview.character.overall

import com.nemesis.rio.domain.profile.Character
import kotlinx.datetime.LocalDateTime

data class CharacterOverallData(
    val character: Character,
    val mythicPlusData: CharacterOverallMythicPlusData,
    val raidingData: CharacterOverallRaidingData,
    val lastUpdateDateTime: LocalDateTime
)
