package com.nemesis.rio.domain.raiding.progress

import com.nemesis.rio.domain.raiding.Difficulty

typealias Kills = Int

typealias RaidProgress = Map<Difficulty, Kills>

fun RaidProgress.bestProgress() =
    filterValues { kills -> kills != 0 }.maxByOrNull { it.key }?.toPair() ?: Difficulty.NORMAL to 0
