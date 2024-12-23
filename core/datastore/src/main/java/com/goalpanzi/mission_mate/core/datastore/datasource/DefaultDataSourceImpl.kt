package com.goalpanzi.mission_mate.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.goalpanzi.mission_mate.core.datastore.model.UserProfileDto
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
        val FCM_TOKEN = stringPreferencesKey("FCM_TOKEN")
    }

    override fun clearUserData(): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        emit(Unit)
    }

    override fun setUserProfile(data: UserProfileDto): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_NICKNAME] = data.nickname
            preferences[PreferencesKey.USER_CHARACTER] = data.characterType
        }
        emit(Unit)
    }

    override fun getUserProfile(): Flow<UserProfileDto?> = dataStore.data.map { preferences ->
        val nickname = preferences[PreferencesKey.USER_NICKNAME]
        val character = preferences[PreferencesKey.USER_CHARACTER]
        if (nickname != null && character != null) {
            UserProfileDto(
                nickname,
                character
            )
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

    override fun setFcmToken(data: String): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.FCM_TOKEN] = data
        }
        emit(Unit)
    }

    override fun getFcmToken(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.FCM_TOKEN]
    }
}
