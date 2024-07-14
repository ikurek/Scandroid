package com.ikurek.scandroid.analytics.di

import com.ikurek.scandroid.analytics.Analytics
import com.ikurek.scandroid.analytics.ErrorTracker
import com.ikurek.scandroid.analytics.ScreenTracker
import com.ikurek.scandroid.analytics.internal.FirebaseAnalytics
import com.ikurek.scandroid.analytics.internal.FirebaseErrorTracker
import com.ikurek.scandroid.analytics.internal.FirebaseScreenTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AnalyticsModule {

    @Binds
    fun bindsAnalytics(impl: FirebaseAnalytics): Analytics

    @Binds
    fun bindsErrorTracker(impl: FirebaseErrorTracker): ErrorTracker

    @Binds
    fun bindsScreenTracker(impl: FirebaseScreenTracker): ScreenTracker
}
