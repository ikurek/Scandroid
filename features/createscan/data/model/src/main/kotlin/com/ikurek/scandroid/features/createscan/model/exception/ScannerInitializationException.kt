package com.ikurek.scandroid.features.createscan.model.exception

data class ScannerInitializationException(
    override val message: String?,
    override val cause: Throwable?
) : RuntimeException()
