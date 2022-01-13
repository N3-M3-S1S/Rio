package com.nemesis.rio.data.raiding.achievements.database

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.data.progress.database.CharacterProgressEntity
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.domain.raiding.achievements.AheadOfTheCurve
import com.nemesis.rio.domain.raiding.achievements.CuttingEdge
import com.nemesis.rio.domain.raiding.achievements.RaidAchievement
import kotlinx.datetime.Instant

@Entity(
    primaryKeys = ["raid", ProfileEntity.ID_COLUMN_NAME],
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class RaidAchievementsEntity(
    val raid: Raid,
    val aotcAchievedAt: Instant,
    val ceAchievedAt: Instant?,
    characterID: Long,
) : CharacterProgressEntity(characterID)

@OptIn(ExperimentalStdlibApi::class)
internal fun RaidAchievementsEntity.toRaidAchievementsList() = buildList {
    add(AheadOfTheCurve(aotcAchievedAt))
    ceAchievedAt?.let { add(CuttingEdge(it)) }
}

internal fun Map<Raid, List<RaidAchievement>>.toRaidAchievementsEntities(characterId: Long) =
    map { (raid, achievements) ->
        val aotcDateTime = achievements.find { it is AheadOfTheCurve }?.achievedAt
        checkNotNull(aotcDateTime)
        val ceDateTime = achievements.find { it is CuttingEdge }?.achievedAt
        RaidAchievementsEntity(
            raid,
            aotcDateTime,
            ceDateTime,
            characterId
        )
    }

// new raids go first
internal fun List<RaidAchievementsEntity>.toRaidAchievementsMap() =
    sortedBy { it.raid }.associate { it.raid to it.toRaidAchievementsList() }
