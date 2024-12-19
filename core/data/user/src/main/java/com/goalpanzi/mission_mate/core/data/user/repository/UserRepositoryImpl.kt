package com.goalpanzi.mission_mate.core.data.user.repository

import com.goalpanzi.mission_mate.core.data.common.handleResult
import com.goalpanzi.mission_mate.core.data.common.mapper.toResponse
import com.goalpanzi.mission_mate.core.data.user.FcmTokenManager
import com.goalpanzi.mission_mate.core.data.user.mapper.toDto
import com.goalpanzi.mission_mate.core.data.user.mapper.toModel
import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import com.goalpanzi.mission_mate.core.network.model.request.SaveProfileRequest
import com.goalpanzi.mission_mate.core.network.model.request.UpdateDeviceTokenRequest
import com.goalpanzi.mission_mate.core.network.service.ProfileService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val profileService: ProfileService,
    private val defaultDataSource: DefaultDataSource,
    private val fcmTokenManager: FcmTokenManager
) : UserRepository {
    override suspend fun saveProfile(
        nickname: String,
        type: CharacterType,
        isEqualNickname: Boolean
    ): DomainResult<Unit> = handleResult {
        val request = SaveProfileRequest.createRequest(
            if (isEqualNickname) null else nickname,
            type.toResponse()
        )
        profileService.saveProfile(request)
    }

    override suspend fun updateFcmToken(fcmToken: String): DomainResult<Unit> = handleResult {
        val request = UpdateDeviceTokenRequest(fcmToken)
        profileService.updateDeviceToken(request)
    }

    override fun getFcmToken(): Flow<String> = fcmTokenManager.getFcmToken()

    override fun clearUserData(): Flow<Unit> = defaultDataSource.clearUserData()

    override fun setUserProfile(data: UserProfile): Flow<Unit> = defaultDataSource.setUserProfile(data.toDto())

    override fun getUserProfile(): Flow<UserProfile?> = defaultDataSource.getUserProfile().map { it?.toModel() }

    override fun setMemberId(data: Long): Flow<Unit> = defaultDataSource.setMemberId(data)

    override fun getMemberId(): Flow<Long?> = defaultDataSource.getMemberId()

}
