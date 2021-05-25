package com.nemesis.rio.domain.raiding.achievements

import kotlinx.datetime.LocalDateTime

sealed class RaidAchievement : Comparable<RaidAchievement> {
    abstract val date: LocalDateTime

    override fun compareTo(other: RaidAchievement): Int {
        fun getAchievementWeight(achievement: RaidAchievement) = when (achievement) {
            is AheadOfTheCurve -> 0
            is CuttingEdge -> 1
        }
        return compareValues(getAchievementWeight(this), getAchievementWeight(other))
    }
}

data class AheadOfTheCurve(override val date: LocalDateTime) : RaidAchievement()

data class CuttingEdge(override val date: LocalDateTime) : RaidAchievement()

fun List<RaidAchievement>.bestAchievementOrNull() = maxOrNull()
