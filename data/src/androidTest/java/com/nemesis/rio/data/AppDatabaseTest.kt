package com.nemesis.rio.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.nemesis.rio.data.database.AppDatabase

abstract class AppDatabaseTest {

    val appDatabase = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        AppDatabase::class.java
    ).build()

}


