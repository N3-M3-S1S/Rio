package com.nemesis.rio.data.game.database

import androidx.room.TypeConverter
import com.nemesis.rio.domain.game.Expansion

object ExpansionConverters {

    @TypeConverter
    fun expansionToId(expansion: Expansion): Int = when (expansion) {
        Expansion.LEGION -> 6
        Expansion.BFA -> 7
        Expansion.SHADOWLANDS -> 8
    }

    @TypeConverter
    fun idToExpansion(expansionId: Int) =
        enumValues<Expansion>().first { expansionToId(it) == expansionId }
}
