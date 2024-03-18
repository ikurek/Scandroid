package com.ikurek.scandroid.features.createscan.usecase

import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CreateScanNameFromCurrentDate @Inject internal constructor(
    private val clock: Clock
) {

    operator fun invoke(): String {
        val now = LocalDateTime.now(clock)
        val date = now.toLocalDate()
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
        val time = now.toLocalTime().truncatedTo(ChronoUnit.SECONDS)
            .format(DateTimeFormatter.ISO_LOCAL_TIME)
        return "$date $time"
    }
}
