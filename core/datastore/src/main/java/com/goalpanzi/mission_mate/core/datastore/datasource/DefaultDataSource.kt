package com.goalpanzi.mission_mate.core.datastore.datasource

import com.goalpanzi.mission_mate.core.datastore.model.UserProfileDto
import kotlinx.coroutines.flow.Flow

interface DefaultDataSource {
    fun clearUserData() : Flow<Unit>
    fun setUserProfile(data: UserProfileDto) : Flow<Unit>
    fun getUserProfile() : Flow<UserProfileDto?>
    fun getViewedTooltip() : Flow<Boolean>
    fun setViewedTooltip() : Flow<Unit>
    fun setMemberId(data: Long) : Flow<Unit>
    fun getMemberId() : Flow<Long?>
    fun setFcmToken(data: String) : Flow<Unit>
    fun getFcmToken() : Flow<String?>
}
