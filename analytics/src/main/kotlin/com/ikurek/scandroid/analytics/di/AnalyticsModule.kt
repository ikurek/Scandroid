package com.ikurek.scandroid.analytics.di

import com.ikurek.scandroid.analytics.FirebaseScreenTracker
import com.ikurek.scandroid.analytics.ScreenTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AnalyticsModule {

    @Binds
    fun bindsScreenTracker(impl: FirebaseScreenTracker): ScreenTracker
}
