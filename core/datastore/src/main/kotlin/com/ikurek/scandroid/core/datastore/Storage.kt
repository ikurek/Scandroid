package com.ikurek.scandroid.core.datastore

interface Storage {

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?

    suspend fun putInt(key: String, value: Int)

    suspend fun getInt(key: String): Int?

    suspend fun putFloat(key: String, value: Float)

    suspend fun getFloat(key: String): Float?

    suspend fun putDouble(key: String, value: Double)

    suspend fun getDouble(key: String): Double?

    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun getBoolean(key: String): Boolean?
}
