package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface DefaultRepository {
    fun clearUserData() : Flow<Unit>
    fun setUserProfile(data: UserProfile) : Flow<Unit>
    fun getUserProfile() : Flow<UserProfile?>
    fun getViewedTooltip() : Flow<Boolean>
    fun setViewedTooltip() : Flow<Unit>
    fun setMemberId(data: Long) : Flow<Unit>
    fun getMemberId() : Flow<Long?>
}