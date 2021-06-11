package com.nemesis.rio.data.mplus.scores.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore


abstract class MythicPlusScoreBaseEntity(
    @ColumnInfo(name = SCORE_COLUMN)
    val score: MythicPlusScore,
    @ColumnInfo(name = SEASON_ID_COLUMN)
    val seasonId: Long,
    @ColumnInfo(name = CHARACTER_ID_COLUMN)
    val characterId: Long
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val SCORE_COLUMN = "score"
        const val SEASON_ID_COLUMN = "seasonId"
        const val CHARACTER_ID_COLUMN = "characterId"
    }

}
