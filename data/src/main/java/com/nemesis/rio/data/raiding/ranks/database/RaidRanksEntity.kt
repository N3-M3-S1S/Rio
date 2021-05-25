package com.nemesis.rio.data.raiding.ranks.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.data.profile.guild.database.GuildEntity
import com.nemesis.rio.data.progress.database.GuildProgressEntity
import com.nemesis.rio.domain.raiding.Difficulty
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.ranks.RaidRanks
import com.nemesis.rio.domain.ranks.Ranks

@Entity(
    primaryKeys = ["raid", "difficulty", ProfileEntity.ID_COLUMN_NAME], foreignKeys = [ForeignKey(
        entity = GuildEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class RaidRanksEntity(
    val raid: Raid,
    val difficulty: Difficulty,
    @Embedded val ranks: Ranks,
    guildID: Long
) : GuildProgressEntity(guildID)

internal fun Map<Raid, RaidRanks>.toRaidRanksEntities(guildID: Long) =
    flatMap { (raid, raidRanks) ->
        raidRanks.map { (difficulty, ranks) ->
            RaidRanksEntity(
                raid,
                difficulty,
                ranks,
                guildID
            )
        }
    }

internal fun List<RaidRanksEntity>.toRaidRanks() =
    associate { it.difficulty to it.ranks }

// new raids go first
internal fun List<RaidRanksEntity>.toRaidWithRanks() =
    sortedBy { it.raid }.groupBy { it.raid }.mapValues { it.value.toRaidRanks() }
