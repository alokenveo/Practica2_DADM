package unex.cum.reservasgo_dadm.network

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey("USER_ID")] = userId
        }
    }

    suspend fun getUserId(): Int {
        return dataStore.data.map { preferences ->
            preferences[intPreferencesKey("USER_ID")] ?: 0
        }.first()
    }
}
