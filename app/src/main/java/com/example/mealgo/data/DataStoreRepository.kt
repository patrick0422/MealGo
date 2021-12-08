package com.example.mealgo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mealgo.data.school.model.School
import com.example.mealgo.util.Constants.Companion.PREFERENCES_NAME
import com.example.mealgo.util.Constants.Companion.PREFERENCES_SCHOOL_CODE
import com.example.mealgo.util.Constants.Companion.PREFERENCES_SCHOOL_LOCATION
import com.example.mealgo.util.Constants.Companion.PREFERENCES_SCHOOL_NAME
import com.example.mealgo.util.Constants.Companion.PREFERENCES_SIDO_CODE
import com.example.mealgo.util.Constants.Companion.PREFERENCES_SIDO_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val sidoCode = stringPreferencesKey(PREFERENCES_SIDO_CODE)
        val sidoName = stringPreferencesKey(PREFERENCES_SIDO_NAME)
        val schoolCode = stringPreferencesKey(PREFERENCES_SCHOOL_CODE)
        val schoolName = stringPreferencesKey(PREFERENCES_SCHOOL_NAME)
        val schoolLocation = stringPreferencesKey(PREFERENCES_SCHOOL_LOCATION)
    }

    suspend fun saveSchoolData(school: School) {
        context.dataStore.edit { preferences ->
            with(school) {
                preferences[PreferenceKeys.sidoCode] = sidoCode
                preferences[PreferenceKeys.sidoName] = sidoName
                preferences[PreferenceKeys.schoolCode] = schoolCode
                preferences[PreferenceKeys.schoolName] = schoolName
                preferences[PreferenceKeys.schoolLocation] = schoolLocation
            }
        }
    }

    val loadSchoolInfo: Flow<School> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            School(
                preferences[PreferenceKeys.sidoCode] ?: "Unknown",
                preferences[PreferenceKeys.sidoName] ?: "Unknown",
                preferences[PreferenceKeys.schoolCode] ?: "Unknown",
                preferences[PreferenceKeys.schoolName] ?: "Unknown",
                preferences[PreferenceKeys.schoolLocation] ?: "Unknown"
            )
        }
}
