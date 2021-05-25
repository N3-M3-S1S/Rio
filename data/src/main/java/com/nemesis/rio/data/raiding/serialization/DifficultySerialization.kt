package com.nemesis.rio.data.raiding.serialization

import com.nemesis.rio.domain.raiding.Difficulty

internal object DifficultySerialization {

    fun getRaidProgressDifficultyJsonValue(difficulty: Difficulty): String = when (difficulty) {
        Difficulty.NORMAL -> "normal_bosses_killed"
        Difficulty.HEROIC -> "heroic_bosses_killed"
        Difficulty.MYTHIC -> "mythic_bosses_killed"
    }

    fun getRaidRanksDifficultyJsonValue(difficulty: Difficulty) = when (difficulty) {
        Difficulty.NORMAL -> "normal"
        Difficulty.HEROIC -> "heroic"
        Difficulty.MYTHIC -> "mythic"
    }
}
