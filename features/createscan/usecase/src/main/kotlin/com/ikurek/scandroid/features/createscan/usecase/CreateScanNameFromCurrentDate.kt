package com.ikurek.scandroid.features.createscan.usecase

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CreateScanNameFromCurrentDate @Inject internal constructor() {

    operator fun invoke(createdAt: ZonedDateTime): String {
        val date = createdAt.toLocalDate()
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
        val time = createdAt.toLocalTime().truncatedTo(ChronoUnit.SECONDS)
            .format(DateTimeFormatter.ISO_LOCAL_TIME)
        return "$date $time"
    }
}
