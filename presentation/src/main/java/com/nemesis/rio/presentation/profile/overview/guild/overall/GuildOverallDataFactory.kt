package com.nemesis.rio.presentation.profile.overview.guild.overall

import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.profile.update.usecase.GetProfileLastUpdateDateTime
import com.nemesis.rio.domain.raiding.progress.bestProgress
import com.nemesis.rio.domain.raiding.progress.usecase.GetProgressForRaid
import com.nemesis.rio.domain.raiding.ranks.usecase.GetRanksForRaid
import com.nemesis.rio.domain.raiding.usecase.GetCurrentRaid

class GuildOverallDataFactory(
    private val getCurrentRaid: GetCurrentRaid,
    private val getProgressForRaid: GetProgressForRaid,
    private val getRanksForRaid: GetRanksForRaid,
    private val getProfileLastRefreshDateTime: GetProfileLastUpdateDateTime<Guild>,
) {

    suspend fun getGuildOverallData(guild: Guild) =
        GuildOverallData(
            guild,
            getRaidingDataForCurrentRaid(guild),
            getProfileLastRefreshDateTime(guild)
        )

    private suspend fun getRaidingDataForCurrentRaid(guild: Guild): GuildOverallRaidingData {
        val currentRaid = getCurrentRaid()
        val (difficulty, bossesKilled) = getProgressForRaid(guild, currentRaid).bestProgress()
        val ranksForDifficulty = getRanksForRaid(guild, currentRaid)[difficulty]
        return GuildOverallRaidingData(currentRaid, difficulty, bossesKilled, ranksForDifficulty)
    }
}
