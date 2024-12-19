package com.goalpanzi.mission_mate.core.domain.user.repository

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveProfile(nickname: String, type: CharacterType, isEqualNickname : Boolean): DomainResult<Unit>
    suspend fun updateFcmToken(fcmToken : String): DomainResult<Unit>
    fun getFcmToken(): Flow<String>
    fun clearUserData() : Flow<Unit>
    fun setUserProfile(data: UserProfile) : Flow<Unit>
    fun getUserProfile() : Flow<UserProfile?>
    fun setMemberId(data: Long) : Flow<Unit>
    fun getMemberId() : Flow<Long?>
}
