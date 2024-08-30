package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.data.handleResult
import com.goalpanzi.mission_mate.core.data.mapper.toResponse
import com.goalpanzi.mission_mate.core.domain.repository.ProfileRepository
import com.goalpanzi.mission_mate.core.network.service.ProfileService
import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.network.model.request.SaveProfileRequest
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService
) : ProfileRepository {
    override suspend fun saveProfile(
        nickname: String,
        type: CharacterType,
        isEqualNickname: Boolean
    ): DomainResult<Unit> = handleResult {
        val request = SaveProfileRequest.createRequest(if(isEqualNickname)null else nickname, type.toResponse())
        profileService.saveProfile(request)
    }
}