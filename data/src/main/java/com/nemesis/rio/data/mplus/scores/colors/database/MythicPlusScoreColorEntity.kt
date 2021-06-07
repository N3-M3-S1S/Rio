package com.nemesis.rio.data.mplus.scores.colors.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor

@Entity(
    tableName = MythicPlusScoreColorEntity.TABLE_NAME,
    primaryKeys = [MythicPlusScoreColorEntity.SEASON_ID_COLUMN, MythicPlusScoreColorEntity.SCORE_COLUMN],
    foreignKeys = [ForeignKey(
        entity = SeasonEntity::class,
        parentColumns = ["id"],
        childColumns = [MythicPlusScoreColorEntity.SEASON_ID_COLUMN]
    )]
)
data class MythicPlusScoreColorEntity(
    @ColumnInfo(name = SEASON_ID_COLUMN)
    val seasonId: Int,
    @ColumnInfo(name = SCORE_COLUMN)
    val score: MythicPlusScore,
    @ColumnInfo(name = HEX_COLOR_COLUMN)
    val hexColor: HexColor
) {
    companion object {
        const val TABLE_NAME = "mplus_scores_colors"
        const val SEASON_ID_COLUMN = "season_id"
        const val SCORE_COLUMN = "score"
        const val HEX_COLOR_COLUMN = "hex_color"
    }

}
