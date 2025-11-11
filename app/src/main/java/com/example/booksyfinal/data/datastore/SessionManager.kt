package com.example.booksyfinal.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "booksy_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val KEY_LOGGED = booleanPreferencesKey("is_logged")
        private val KEY_NAME = stringPreferencesKey("name")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PASSWORD = stringPreferencesKey("password")
        private val KEY_IMAGE = stringPreferencesKey("image")
    }

    // 🔹 Guardar usuario
    suspend fun saveUser(name: String, email: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_NAME] = name
            prefs[KEY_EMAIL] = email
            prefs[KEY_PASSWORD] = password
            prefs[KEY_LOGGED] = true
        }
    }

    // 🔹 Guardar imagen
    suspend fun saveImage(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_IMAGE] = uri
        }
    }

    // 🔹 Obtener valores como Flow
    val name: Flow<String> = context.dataStore.data.map { it[KEY_NAME] ?: "" }
    val email: Flow<String> = context.dataStore.data.map { it[KEY_EMAIL] ?: "" }
    val password: Flow<String> = context.dataStore.data.map { it[KEY_PASSWORD] ?: "" }
    val image: Flow<String> = context.dataStore.data.map { it[KEY_IMAGE] ?: "" }
    val isLogged: Flow<Boolean> = context.dataStore.data.map { it[KEY_LOGGED] ?: false }

    // 🔹 Logout
    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED] = false
        }
    }
}
