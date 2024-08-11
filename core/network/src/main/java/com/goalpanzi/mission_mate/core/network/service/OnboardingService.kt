package com.goalpanzi.mission_mate.core.network.service

import com.luckyoct.core.model.request.CreateMissionRequest
import com.luckyoct.core.model.response.MissionDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OnboardingService {
    @POST("/api/missions")
    suspend fun createMission(
        @Body request: CreateMissionRequest
    ): Response<MissionDetailResponse>

    @GET("/api/missions")
    suspend fun getMissionByInvitationCode(
        @Query("invitationCode") invitationCode : String
    ) : Response<MissionDetailResponse>
}