package com.nemesis.rio.data.raiding.progress.database

import androidx.room.Entity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.data.raiding.createRaidProgress
import com.nemesis.rio.data.raiding.getOrZero
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.progress.RaidProgress

@Entity(primaryKeys = ["profileType", ProfileEntity.ID_COLUMN_NAME, "raid"])
class RaidProgressEntity(
    val raid: Raid,
    val normalKills: Int,
    val heroicKills: Int,
    val mythicKills: Int,
    val profileID: Long,
    val profileType: Int
) {

    companion object {
        const val PROFILE_TYPE_CHARACTER = 0
        const val PROFILE_TYPE_GUILD = 1
    }
}

// new raids go first
internal fun List<RaidProgressEntity>.toRaidProgressMap() =
    sortedBy { it.raid }.associate { it.raid to it.toRaidProgress() }

@OptIn(ExperimentalStdlibApi::class)
internal fun RaidProgressEntity.toRaidProgress() =
    createRaidProgress(normalKills, heroicKills, mythicKills)

internal fun Map<Raid, RaidProgress>.toRaidProgressEntities(profileID: Long, profileType: Int) =
    map { (raid, progress) ->
        progress.toRaidProgressEntity(raid, profileID, profileType)
    }

private fun RaidProgress.toRaidProgressEntity(raid: Raid, profileID: Long, profileType: Int) =
    RaidProgressEntity(
        raid,
        getOrZero(Difficulty.NORMAL),
        getOrZero(Difficulty.HEROIC),
        getOrZero(Difficulty.MYTHIC),
        profileID,
        profileType
    )
