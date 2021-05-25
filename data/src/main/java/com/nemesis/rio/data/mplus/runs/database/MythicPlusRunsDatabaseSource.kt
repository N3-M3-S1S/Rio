package com.nemesis.rio.data.mplus.runs.database

import com.nemesis.rio.data.profile.database.ProfileIDProvider
import com.nemesis.rio.data.profile.database.withProfileID
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun
import com.nemesis.rio.domain.mplus.runs.MythicPlusRunsSource
import com.nemesis.rio.domain.profile.character.Character

class MythicPlusRunsDatabaseSource(
    private val runsDao: MythicPlusRunsDao,
    private val profileIDProvider: ProfileIDProvider<Character>,
) : MythicPlusRunsSource {

    override suspend fun getRunsForCurrentSeason(character: Character): List<MythicPlusRun> =
        profileIDProvider.withProfileID(character) {
            runsDao.getMythicPlusRunsPojos(it).toMythicPlusRuns()
        }
}
