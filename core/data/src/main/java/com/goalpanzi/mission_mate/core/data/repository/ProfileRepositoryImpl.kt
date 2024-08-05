package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.ProfileRepository
import com.goalpanzi.mission_mate.core.network.service.ProfileService
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.request.SaveProfileRequest
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService
): ProfileRepository {
    override suspend fun saveProfile(nickname: String, index: Int): NetworkResult<Unit> = handleResult {
        val request = SaveProfileRequest.createRequest(nickname, index)
        profileService.saveProfile(request)
    }
}