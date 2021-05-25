package com.nemesis.rio.data.profile.database

import com.nemesis.rio.data.AppDatabaseTest
import sharedTest.createTestCharacter
import sharedTest.createTestGuild

suspend fun AppDatabaseTest.createTestCharacterInDatabase(name: String = "testCharacter"): Long =
    appDatabase.characterDao.saveOrUpdate(createTestCharacter(name))

suspend fun AppDatabaseTest.createTestGuildInDatabase(name: String = "testGuild"): Long =
    appDatabase.guildDao.saveOrUpdate(createTestGuild(name))
