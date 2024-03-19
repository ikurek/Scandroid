package com.ikurek.scandroid.core.database.room.converters

import androidx.room.TypeConverter
import java.util.UUID

internal class UuidConverter {
    @TypeConverter
    fun fromString(value: String): UUID = UUID.fromString(value)

    @TypeConverter
    fun toString(uuid: UUID): String = uuid.toString()
}
