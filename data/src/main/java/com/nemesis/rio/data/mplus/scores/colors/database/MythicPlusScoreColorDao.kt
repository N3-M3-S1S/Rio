package com.nemesis.rio.data.mplus.scores.colors.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nemesis.rio.domain.mplus.scores.MythicPlusScore
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.domain.mplus.seasons.Season

@Dao
interface MythicPlusScoreColorDao {

    @Query("SELECT hex_color FROM mplus_scores_colors WHERE season_id = (SELECT id FROM seasons WHERE name = :season) AND score <= :score ORDER BY score DESC LIMIT 1")
    suspend fun getHexColorForMythicPlusScore(score: MythicPlusScore, season: Season): HexColor?

    @Query("DELETE FROM mplus_scores_colors")
    suspend fun deleteAll()

    @Insert
    suspend fun saveAll(entities: List<MythicPlusScoreColorEntity>)

}
