package com.nemesis.rio.presentation.profile.overview.guild.raiding

import com.nemesis.rio.domain.profile.Guild
import com.nemesis.rio.domain.raiding.progress.usecase.GetAllRaidProgress
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressItem
import com.nemesis.rio.presentation.profile.overview.raiding.RaidProgressListData

class GuildRaidProgressListDataFactory(private val getProgressForAllRaids: GetAllRaidProgress) {

    suspend fun getGuildRaidProgressListData(guild: Guild): RaidProgressListData =
        getProgressForAllRaids(guild)
            .map { (raid, progress) -> RaidProgressItem(raid, progress) }
            .groupBy { it.raid.expansion }
}
