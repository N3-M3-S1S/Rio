package com.nemesis.rio.data.profile.character.api

import com.nemesis.rio.data.mplus.seasons.database.SeasonsDao
import com.nemesis.rio.data.profile.api.ProfileSearchFields
import com.nemesis.rio.data.raiding.serialization.RaidSerialization
import com.nemesis.rio.domain.raiding.Raid
import com.nemesis.rio.utils.joinStrings

@JvmInline
value class CharacterSearchFields(private val apiFieldsQueryParameter: String) {

    internal companion object {
        const val GEAR = "gear"
        const val GUILD = "guild"
        const val COVENANT = "covenant"
        const val RAID_ACHIEVEMENT_CURVE = "raid_achievement_curve"
        const val MYTHIC_PLUS_SCORES_BY_SEASON = "mythic_plus_scores_by_season"
        const val MYTHIC_PLUS_RANKS = "mythic_plus_ranks"
        const val MYTHIC_PLUS_BEST_RUNS = "mythic_plus_best_runs"
        const val MYTHIC_PLUS_RECENT_RUNS = "mythic_plus_recent_runs"
    }

    class Factory(private val seasonsDao: SeasonsDao) {
        private var characterSearchFields: CharacterSearchFields? = null

        internal suspend fun create(): CharacterSearchFields {
            if (characterSearchFields == null) {
                characterSearchFields =
                    CharacterSearchFields(buildApiFieldsQueryParameter())
            }
            return characterSearchFields!!
        }

        private suspend fun buildApiFieldsQueryParameter(): String =
            joinStrings {
                add(GUILD)
                add(GEAR)
                add(COVENANT)

                add(MYTHIC_PLUS_RANKS)
                add(MYTHIC_PLUS_RECENT_RUNS)
                add("$MYTHIC_PLUS_BEST_RUNS:all")
                val seasonsJsonValuesString = seasonsDao.getSeasonApiValues()
                    .joinToString(separator = ":", prefix = ":")
                add(MYTHIC_PLUS_SCORES_BY_SEASON + seasonsJsonValuesString)

                add(ProfileSearchFields.RAID_PROGRESSION)
                val raidsJsonValuesString = enumValues<Raid>().joinToString(
                    separator = ":",
                    prefix = ":",
                    transform = RaidSerialization::getRaidJsonValue
                )
                add(
                    RAID_ACHIEVEMENT_CURVE + raidsJsonValuesString
                )
            }
    }
}
