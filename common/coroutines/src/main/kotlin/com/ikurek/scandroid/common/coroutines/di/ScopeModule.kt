package com.ikurek.scandroid.common.coroutines.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppCoroutineScope

@Module
@InstallIn(SingletonComponent::class)
internal object ScopeModule {

    @AppCoroutineScope
    @Singleton
    @Provides
    fun providesAppCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}
