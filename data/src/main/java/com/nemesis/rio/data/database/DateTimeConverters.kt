package com.nemesis.rio.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import java.time.Duration

object DateTimeConverters {

    @TypeConverter
    fun localDateTimeToColumnValue(localDateTime: LocalDateTime?) = localDateTime?.toString()

    @TypeConverter
    fun columnValueToLocalDateTime(columnValue: String?) =
        columnValue?.let(LocalDateTime::parse)

    @TypeConverter
    fun durationToMillis(duration: Duration) = duration.toMillis()

    @TypeConverter
    fun millisToDuration(millis: Long): Duration = Duration.ofMillis(millis)
}
