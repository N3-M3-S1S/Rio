package com.nemesis.rio.data.mplus.ranks.database

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.nemesis.rio.data.progress.database.CharacterProgressEntity
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.ranks.Ranks

abstract class BaseMythicPlusRanksEntity(
    @Embedded val ranks: Ranks,
    val scope: MythicPlusRanksScope,
    characterID: Long
) : CharacterProgressEntity(characterID) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
