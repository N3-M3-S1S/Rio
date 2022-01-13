package com.nemesis.rio.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import java.time.Duration

object DateTimeConverters {

    @TypeConverter
    fun durationToMillis(duration: Duration) = duration.toMillis()

    @TypeConverter
    fun millisToDuration(millis: Long): Duration = Duration.ofMillis(millis)

    @TypeConverter
    fun instantToLong(instant: Instant?) = instant?.toEpochMilliseconds()

    @TypeConverter
    fun longToInstant(instantLong: Long?) = instantLong?.let(Instant::fromEpochMilliseconds)
}
