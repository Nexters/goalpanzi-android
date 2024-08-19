package com.goalpanzi.mission_mate.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MissionDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : MissionDataSource {

    object PreferencesKey {
        val MISSION_IS_JOINED = booleanPreferencesKey("MISSION_IS_JOINED")
    }

    override fun clearMissionData(): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        emit(Unit)
    }

    override fun setIsMissionJoined(data: Boolean): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.MISSION_IS_JOINED] = data
        }
        emit(Unit)
    }

    override fun getIsMissionJoined(): Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.MISSION_IS_JOINED]
    }
}