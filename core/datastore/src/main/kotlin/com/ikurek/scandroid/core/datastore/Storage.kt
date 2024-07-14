package com.ikurek.scandroid.core.datastore

import kotlinx.coroutines.flow.Flow

@Suppress("TooManyFunctions")
interface Storage {

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?

    fun observeString(key: String): Flow<String?>

    suspend fun putInt(key: String, value: Int)

    suspend fun getInt(key: String): Int?

    fun observeInt(key: String): Flow<Int?>

    suspend fun putFloat(key: String, value: Float)

    suspend fun getFloat(key: String): Float?

    fun observeFloat(key: String): Flow<Float?>

    suspend fun putDouble(key: String, value: Double)

    suspend fun getDouble(key: String): Double?

    fun observeDouble(key: String): Flow<Double?>

    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun getBoolean(key: String): Boolean?

    fun observeBoolean(key: String): Flow<Boolean?>
}
