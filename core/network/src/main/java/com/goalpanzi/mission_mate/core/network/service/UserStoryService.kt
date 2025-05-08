package com.goalpanzi.mission_mate.core.network.service

import com.goalpanzi.mission_mate.core.network.model.response.VerificationInfoByBlockNumberResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserStoryService {

    @GET("/api/missions/{missionId}/verifications/me/{number}")
    suspend fun getVerificationInfoByBlockNumber(
        @Path("missionId") missionId: Long,
        @Path("number") number: Int,
    ) : Response<VerificationInfoByBlockNumberResponse>
}