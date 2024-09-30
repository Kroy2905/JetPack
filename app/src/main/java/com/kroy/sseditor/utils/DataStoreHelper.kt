package com.kroy.sseditor.utils

import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHelper @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    // Define preference keys
    private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    private val USER_ID = intPreferencesKey("user_id")


    // Get isLoggedIn value as Flow
    val isLoggedInFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    // Get userName value as Flow
    val userIdFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[USER_ID] ?: 0
    }



    // Save values to DataStore
    suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    suspend fun setUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }


}
