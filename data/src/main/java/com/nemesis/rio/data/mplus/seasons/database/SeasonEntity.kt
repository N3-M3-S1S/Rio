package com.nemesis.rio.data.mplus.seasons.database

import androidx.room.*
import com.nemesis.rio.data.game.database.ExpansionConverters
import com.nemesis.rio.domain.game.Expansion

@Entity(tableName = SeasonEntity.TABLE_NAME, indices = [Index("api_value", unique = true)])
data class SeasonEntity(
    @ColumnInfo(name = "api_value") val apiValue: String,
    val name: String,
    @TypeConverters(ExpansionConverters::class) val expansion: Expansion
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val TABLE_NAME = "seasons"
    }

}
