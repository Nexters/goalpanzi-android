package com.goalpanzi.mission_mate.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.goalpanzi.core.model.CharacterType
import com.goalpanzi.core.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DefaultDataSource {

    object PreferencesKey {
        val USER_NICKNAME = stringPreferencesKey("USER_NICKNAME")
        val USER_CHARACTER = stringPreferencesKey("USER_CHARACTER")
        val VIEWED_TOOLTIP = booleanPreferencesKey("VIEWED_TOOLTIP")
        val MEMBER_ID = longPreferencesKey("MEMBER_ID")
    }

    override fun clearUserData(): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        emit(Unit)
    }

    override fun setUserProfile(data: UserProfile): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_NICKNAME] = data.nickname
            preferences[PreferencesKey.USER_CHARACTER] = data.characterType.name.uppercase()
        }
        emit(Unit)
    }

    override fun getUserProfile(): Flow<UserProfile?> = dataStore.data.map { preferences ->
        val nickname = preferences[PreferencesKey.USER_NICKNAME]
        val character = preferences[PreferencesKey.USER_CHARACTER]
        if (nickname != null && character != null) {
            UserProfile(nickname, CharacterType.valueOf(character))
        } else {
            null
        }
    }

    override fun getViewedTooltip(): Flow<Boolean> = dataStore.data.map { preferences ->
        val viewed = preferences[PreferencesKey.VIEWED_TOOLTIP]
        viewed ?: false
    }

    override fun setViewedTooltip(): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.VIEWED_TOOLTIP] = true
        }
        emit(Unit)
    }
    
    override fun setMemberId(data: Long): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.MEMBER_ID] = data
        }
        emit(Unit)
    }

    override fun getMemberId(): Flow<Long?> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.MEMBER_ID]
    }
}