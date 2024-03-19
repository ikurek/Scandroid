package com.ikurek.scandroid.core.database.room.converters

import androidx.room.TypeConverter
import java.time.ZonedDateTime

internal class ZonedDateTimeConverter {
    @TypeConverter
    fun fromString(value: String): ZonedDateTime = ZonedDateTime.parse(value)

    @TypeConverter
    fun toString(zonedDateTime: ZonedDateTime): String = zonedDateTime.toString()
}
