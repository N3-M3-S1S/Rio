package com.nemesis.rio.data.mplus.runs.database

import androidx.room.Embedded
import androidx.room.Relation
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun

data class MythicPlusRunPojo(
    @Embedded val runEntity: MythicPlusRunEntity,

    @Relation(
        parentColumn = MythicPlusRunEntity.ID_COLUMN_NAME,
        entityColumn = MythicPlusRunEntity.ID_COLUMN_NAME
    )
    val affixesEntity: MythicPlusRunAffixesEntity,
)

internal fun List<MythicPlusRun>.toMythicPlusRunPojos(characterId: Long) =
    map { run -> run.toPojo(characterId) }

internal fun List<MythicPlusRunPojo>.toMythicPlusRuns() = map { pojo -> pojo.toRun() }

private fun MythicPlusRun.toPojo(characterId: Long): MythicPlusRunPojo {
    val runEntity = this.toRunEntity(characterId)
    val affixesEntity = affixes.toAffixesEntity()
    return MythicPlusRunPojo(runEntity, affixesEntity)
}

private fun MythicPlusRunPojo.toRun(): MythicPlusRun {
    val affixes = affixesEntity.toAffixesMap()
    with(runEntity) {
        return MythicPlusRun(
            dungeon,
            keystoneLevel,
            date,
            duration,
            keystoneUpgrades,
            score,
            affixes,
            url
        )
    }
}
