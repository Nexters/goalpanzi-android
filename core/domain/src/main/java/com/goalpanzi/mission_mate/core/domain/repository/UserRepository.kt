package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveProfile(nickname: String, type: CharacterType, isEqualNickname : Boolean): DomainResult<Unit>
    fun clearUserData() : Flow<Unit>
    fun setUserProfile(data: UserProfile) : Flow<Unit>
    fun getUserProfile() : Flow<UserProfile?>
    fun setMemberId(data: Long) : Flow<Unit>
    fun getMemberId() : Flow<Long?>
}
