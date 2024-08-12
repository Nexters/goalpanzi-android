package com.goalpanzi.mission_mate.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DefaultDataSource {
    override fun clearUserData(): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        emit(Unit)
    }
}