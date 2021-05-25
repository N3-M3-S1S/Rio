package com.nemesis.rio.data.mplus.runs.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.nemesis.rio.domain.mplus.Affix

@Entity(
    foreignKeys = [ForeignKey(
        entity = MythicPlusRunEntity::class,
        parentColumns = [MythicPlusRunEntity.ID_COLUMN_NAME],
        childColumns = [MythicPlusRunEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)

class MythicPlusRunAffixesEntity(
    val t2: Affix,
    val t4: Affix?,
    val t7: Affix?,
    val t10: Affix?,
) {

    @PrimaryKey
    @ColumnInfo(name = MythicPlusRunEntity.ID_COLUMN_NAME)
    var runID: Long = 0
}

internal fun Map<Affix.Tier, Affix>.toAffixesEntity() =
    MythicPlusRunAffixesEntity(
        getValue(Affix.Tier.T2),
        get(Affix.Tier.T4),
        get(Affix.Tier.T7),
        get(Affix.Tier.T10)
    )

@OptIn(ExperimentalStdlibApi::class)
internal fun MythicPlusRunAffixesEntity.toAffixesMap() = buildMap<Affix.Tier, Affix> {
    put(Affix.Tier.T2, t2)
    t4?.let { put(Affix.Tier.T4, it) }
    t7?.let { put(Affix.Tier.T7, it) }
    t10?.let { put(Affix.Tier.T10, it) }
}
