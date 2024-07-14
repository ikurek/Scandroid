package com.ikurek.scandroid.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

@Suppress("TooManyFunctions")
internal class PreferencesStorage(
    private val dataStore: DataStore<Preferences>
) : Storage {
    override suspend fun putString(key: String, value: String) {
        dataStore.edit { data -> data[stringPreferencesKey(key)] = value }
    }

    override suspend fun getString(key: String): String? =
        dataStore.data.map { data -> data[stringPreferencesKey(key)] }.firstOrNull()

    override fun observeString(key: String): Flow<String?> =
        dataStore.data.map { data -> data[stringPreferencesKey(key)] }

    override suspend fun putInt(key: String, value: Int) {
        dataStore.edit { data -> data[intPreferencesKey(key)] = value }
    }

    override suspend fun getInt(key: String): Int? =
        dataStore.data.map { data -> data[intPreferencesKey(key)] }.firstOrNull()

    override fun observeInt(key: String): Flow<Int?> =
        dataStore.data.map { data -> data[intPreferencesKey(key)] }

    override suspend fun putDouble(key: String, value: Double) {
        dataStore.edit { data -> data[doublePreferencesKey(key)] = value }
    }

    override suspend fun getDouble(key: String): Double? =
        dataStore.data.map { data -> data[doublePreferencesKey(key)] }.firstOrNull()

    override fun observeDouble(key: String): Flow<Double?> =
        dataStore.data.map { data -> data[doublePreferencesKey(key)] }

    override suspend fun putFloat(key: String, value: Float) {
        dataStore.edit { data -> data[floatPreferencesKey(key)] = value }
    }

    override suspend fun getFloat(key: String): Float? =
        dataStore.data.map { data -> data[floatPreferencesKey(key)] }.firstOrNull()

    override fun observeFloat(key: String): Flow<Float?> =
        dataStore.data.map { data -> data[floatPreferencesKey(key)] }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { data -> data[booleanPreferencesKey(key)] = value }
    }

    override suspend fun getBoolean(key: String): Boolean? =
        dataStore.data.map { data -> data[booleanPreferencesKey(key)] }.firstOrNull()

    override fun observeBoolean(key: String): Flow<Boolean?> =
        dataStore.data.map { data -> data[booleanPreferencesKey(key)] }
}
