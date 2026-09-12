package com.navin.personalos.core.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.personalPreferences by preferencesDataStore("personal_preferences")
data class PersonalPreferences(
    val ready: Boolean = false, val name: String = "", val description: String = "", val intents: Set<String> = emptySet(),
    val theme: String = "SYSTEM", val companion: Boolean = true, val reducedMotion: Boolean = false,
    val morning: Boolean = false, val morningMinutes: Int = 480, val evening: Boolean = false, val eveningMinutes: Int = 1140,
    val locked: Boolean = false, val timeoutMinutes: Int = 1, val hideNotificationText: Boolean = false, val lastBackup: Long = 0,
)
@Singleton
class PreferenceStore @Inject constructor(@ApplicationContext context: Context) {
    val store = context.personalPreferences
    val flow = store.data.map { p -> PersonalPreferences(
        p[booleanPreferencesKey("ready")] ?: false, p[stringPreferencesKey("name")] ?: "", p[stringPreferencesKey("description")] ?: "",
        p[stringSetPreferencesKey("intents")] ?: emptySet(), p[stringPreferencesKey("theme")] ?: "SYSTEM",
        p[booleanPreferencesKey("companion")] ?: true, p[booleanPreferencesKey("reducedMotion")] ?: false,
        p[booleanPreferencesKey("morning")] ?: false, p[intPreferencesKey("morningMinutes")] ?: 480,
        p[booleanPreferencesKey("evening")] ?: false, p[intPreferencesKey("eveningMinutes")] ?: 1140,
        p[booleanPreferencesKey("locked")] ?: false, p[intPreferencesKey("timeoutMinutes")] ?: 1,
        p[booleanPreferencesKey("hideNotificationText")] ?: false, p[longPreferencesKey("lastBackup")] ?: 0,
    ) }
    suspend fun text(key: String, value: String) { store.edit { it[stringPreferencesKey(key)] = value } }
    suspend fun flag(key: String, value: Boolean) { store.edit { it[booleanPreferencesKey(key)] = value } }
    suspend fun number(key: String, value: Int) { store.edit { it[intPreferencesKey(key)] = value } }
    suspend fun finishSetup(name: String, intents: Set<String>, morning: Boolean, evening: Boolean) { store.edit {
        it[stringPreferencesKey("name")] = name.trim(); it[stringSetPreferencesKey("intents")] = intents
        it[booleanPreferencesKey("morning")] = morning; it[booleanPreferencesKey("evening")] = evening; it[booleanPreferencesKey("ready")] = true
    } }
    suspend fun clear() { store.edit { it.clear() } }
}
