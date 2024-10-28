package com.goalpanzi.mission_mate.core.network.service

import com.goalpanzi.mission_mate.core.network.model.request.CreateMissionRequest
import com.goalpanzi.mission_mate.core.network.model.request.JoinMissionRequest
import com.goalpanzi.mission_mate.core.network.model.response.MissionDetailResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionsResponse
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

    @GET("/api/mission:joinable")
    suspend fun getMissionByInvitationCode(
        @Query("invitationCode") invitationCode : String
    ) : Response<MissionDetailResponse>

    @POST("/api/mission-members")
    suspend fun joinMission(
        @Body request : JoinMissionRequest
    ) : Response<Unit>

    @GET("/api/mission-members/me")
    suspend fun getJoinedMissions(
        @Query("filter") filter : String = ""
    ) : Response<MissionsResponse>
}
