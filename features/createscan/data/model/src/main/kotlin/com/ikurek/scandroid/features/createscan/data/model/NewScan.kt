package com.ikurek.scandroid.features.createscan.data.model

import java.time.ZonedDateTime
import java.util.UUID

data class NewScan(
    val id: UUID,
    val name: String,
    val description: String?,
    val createdAt: ZonedDateTime
)
