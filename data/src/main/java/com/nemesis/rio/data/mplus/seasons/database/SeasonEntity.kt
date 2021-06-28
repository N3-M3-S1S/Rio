package com.nemesis.rio.data.mplus.seasons.database

import androidx.room.*
import com.nemesis.rio.data.game.database.ExpansionConverters
import com.nemesis.rio.domain.game.Expansion

@Entity(
    tableName = SeasonEntity.TABLE_NAME,
    indices = [Index(SeasonEntity.API_VALUE_COLUMN, unique = true)]
)
data class SeasonEntity(
    @ColumnInfo(name = API_VALUE_COLUMN)
    val apiValue: String,
    @ColumnInfo(name = NAME_COLUMN)
    val name: String,
    @ColumnInfo(name = EXPANSION_COLUMN)
    @TypeConverters(ExpansionConverters::class)
    val expansion: Expansion
) {

    constructor(apiValue: String, name: String, expansion: Expansion, id: Long) : this(
        apiValue,
        name,
        expansion
    ) {
        this.id = id
    }

    companion object {
        const val TABLE_NAME = "seasons"
        const val API_VALUE_COLUMN = "api_value"
        const val NAME_COLUMN = "name"
        const val EXPANSION_COLUMN = "expansion"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
