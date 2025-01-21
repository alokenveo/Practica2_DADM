package unex.cum.reservasgo_dadm.network

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

import androidx.datastore.preferences.core.intPreferencesKey

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    private val dataStore = context.dataStore

    private val USER_ID_KEY = intPreferencesKey("USER_ID")

    // Función para guardar el userId
    suspend fun saveUserId(userId: Int) {
        Log.d("GUARDANDO USUARIO ID", "EL USER ID ES $userId")
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    // Función para obtener el userId, devolviendo un valor predeterminado (0) si no está disponible
    suspend fun getUserId(): Int {
        return dataStore.data
            .map { preferences ->
                preferences[USER_ID_KEY] ?: 0
            }
            .first()
    }
}