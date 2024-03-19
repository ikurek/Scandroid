package com.ikurek.scandroid.features.createscan.data.model.exception

data class UnexpectedScanningError(
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException()
