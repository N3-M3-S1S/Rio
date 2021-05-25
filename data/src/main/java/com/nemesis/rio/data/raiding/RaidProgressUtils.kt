package com.nemesis.rio.data.raiding

import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.progress.RaidProgress

internal fun createRaidProgress(normalKills: Int, heroicKills: Int, mythicKills: Int) = mapOf(
    Difficulty.NORMAL to normalKills,
    Difficulty.HEROIC to heroicKills,
    Difficulty.MYTHIC to mythicKills
)

internal fun RaidProgress.getOrZero(difficulty: Difficulty) = getOrDefault(difficulty, 0)
