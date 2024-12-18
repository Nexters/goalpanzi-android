package com.goalpanzi.mission_mate.core.network.service

import com.goalpanzi.mission_mate.core.network.model.request.SaveProfileRequest
import com.goalpanzi.mission_mate.core.network.model.request.UpdateDeviceTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ProfileService {
    @PATCH("/api/member/profile")
    suspend fun saveProfile(
        @Body request: SaveProfileRequest
    ): Response<Unit>

    @PATCH("/api/member/device-token")
    suspend fun updateDeviceToken(
        @Body request: UpdateDeviceTokenRequest
    ): Response<Unit>

}
