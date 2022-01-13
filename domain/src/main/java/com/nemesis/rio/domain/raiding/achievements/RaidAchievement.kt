package com.nemesis.rio.domain.raiding.achievements

import kotlinx.datetime.Instant

sealed class RaidAchievement : Comparable<RaidAchievement> {
    abstract val achievedAt: Instant

    override fun compareTo(other: RaidAchievement): Int {
        fun getAchievementWeight(achievement: RaidAchievement) = when (achievement) {
            is AheadOfTheCurve -> 0
            is CuttingEdge -> 1
        }
        return compareValues(getAchievementWeight(this), getAchievementWeight(other))
    }
}

data class AheadOfTheCurve(override val achievedAt: Instant) : RaidAchievement()

data class CuttingEdge(override val achievedAt: Instant) : RaidAchievement()

fun List<RaidAchievement>.bestAchievementOrNull() = maxOrNull()
