package com.nemesis.rio.data.raiding.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.data.raiding.progress.database.RaidProgressDao
import com.nemesis.rio.data.raiding.progress.database.RaidProgressEntity
import com.nemesis.rio.data.raiding.progress.database.toRaidProgress
import com.nemesis.rio.data.raiding.progress.database.toRaidProgressMap
import com.nemesis.rio.data.raiding.ranks.database.RaidRanksDao
import com.nemesis.rio.data.raiding.ranks.database.toRaidRanks
import com.nemesis.rio.data.raiding.ranks.database.toRaidWithRanks
import com.nemesis.rio.domain.profile.guild.Guild
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress
import com.nemesis.rio.domain.raiding.ranks.RaidRanks
import com.nemesis.rio.domain.raiding.source.GuildRaidingSource

class GuildRaidingDatabaseSource(
    private val raidProgressDao: RaidProgressDao,
    private val raidRanksDao: RaidRanksDao,
    private val profileIDProvider: ProfileIDProvider<Guild>
) : GuildRaidingSource {

    override suspend fun getProgressForRaid(guild: Guild, raid: Raid): RaidProgress =
        profileIDProvider.withProfileID(guild) {
            raidProgressDao.getRaidProgressEntity(
                raid,
                it,
                RaidProgressEntity.PROFILE_TYPE_GUILD
            )?.toRaidProgress() ?: emptyMap()
        }

    override suspend fun getAllRaidProgress(guild: Guild): Map<Raid, RaidProgress> =
        profileIDProvider.withProfileID(guild) {
            raidProgressDao.getAllRaidProgressEntities(
                it,
                RaidProgressEntity.PROFILE_TYPE_GUILD
            ).toRaidProgressMap()
        }

    override suspend fun getRanksForRaid(guild: Guild, raid: Raid): RaidRanks =
        profileIDProvider.withProfileID(guild) {
            raidRanksDao.getRaidRanksEntitiesForRaid(raid, it).toRaidRanks()
        }

    override suspend fun getAllRaidRanks(guild: Guild): Map<Raid, RaidRanks> =
        profileIDProvider.withProfileID(guild) {
            raidRanksDao.getAllRaidRanksEntities(it).toRaidWithRanks()
        }
}
