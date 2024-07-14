package com.ikurek.scandroid.analytics.data.model

class AnalyticsException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
