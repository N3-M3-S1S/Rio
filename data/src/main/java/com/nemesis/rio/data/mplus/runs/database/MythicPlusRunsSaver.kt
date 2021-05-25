package com.nemesis.rio.data.mplus.runs.database

import com.nemesis.rio.data.progress.database.ProfileProgressSaver
import com.nemesis.rio.domain.mplus.runs.MythicPlusRun

class MythicPlusRunsSaver(private val runsDao: MythicPlusRunsDao) :
    ProfileProgressSaver<List<MythicPlusRun>> {

    override suspend fun saveOrUpdate(progress: List<MythicPlusRun>, profileId: Long) {
        runsDao.deleteAll(profileId)
        runsDao.saveMythicPlusRunPojos(progress.toMythicPlusRunPojos(profileId))
    }
}
