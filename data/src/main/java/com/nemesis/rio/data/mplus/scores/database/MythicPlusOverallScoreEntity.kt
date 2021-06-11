package com.nemesis.rio.data.mplus.scores.database

import androidx.room.Entity
import androidx.room.ForeignKey
import com.nemesis.rio.data.mplus.seasons.database.SeasonEntity
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore


@Entity(
    tableName = MythicPlusOverallScoreEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = [ProfileEntity.ID_COLUMN_NAME],
            childColumns = [MythicPlusScoreBaseEntity.CHARACTER_ID_COLUMN],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SeasonEntity::class,
            parentColumns = ["id"],
            childColumns = [MythicPlusScoreBaseEntity.SEASON_ID_COLUMN]
        )
    ]
)
class MythicPlusOverallScoreEntity(score: MythicPlusScore, seasonId: Long, characterId: Long) :
    MythicPlusScoreBaseEntity(score, seasonId, characterId) {

    companion object {
        const val TABLE_NAME = "MythicPlusOverallScoreEntity"
    }
}
