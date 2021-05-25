package com.nemesis.rio.data.mplus.runs.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.nemesis.rio.data.profile.character.database.CharacterEntity
import com.nemesis.rio.data.profile.database.ProfileEntity
import com.nemesis.rio.data.progress.database.CharacterProgressEntity
import com.nemesis.rio.domain.mplus.Dungeon
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import kotlinx.datetime.LocalDateTime
import java.time.Duration

@Entity(
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = [ProfileEntity.ID_COLUMN_NAME],
        childColumns = [ProfileEntity.ID_COLUMN_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
class MythicPlusRunEntity(
    val dungeon: Dungeon,
    val keystoneLevel: Int,
    val date: LocalDateTime,
    val duration: Duration,
    val keystoneUpgrades: Int,
    val score: Float,
    val url: String,
    characterID: Long,
) : CharacterProgressEntity(characterID) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN_NAME)
    var id: Long = 0

    companion object {
        const val ID_COLUMN_NAME = "runID"
    }
}

internal fun MythicPlusRun.toRunEntity(characterID: Long) =
    MythicPlusRunEntity(
        dungeon,
        keystoneLevel,
        date,
        duration,
        keystoneUpgrades,
        score,
        url,
        characterID
    )
